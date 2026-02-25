import { useEffect, useState } from "react";
import { api } from "../services/api";
import { Link } from "react-router";

interface Movie {
  id: number;
  title: string;
  posterUrl: string;
  releaseDate: string;
  averageUserRating: number;
  averageCriticRating: number;
  genres: string[];
  duration: number;
  reviewCount: number;
}

const StarRating = ({ rating }: { rating: number }) => {
  const pct = (rating / 10) * 100;
  return (
    <span className="d-inline-flex align-items-center gap-1">
      <i className="ti ti-star-filled text-warning" />
      <strong>{rating > 0 ? rating.toFixed(1) : "N/A"}</strong>
      {rating > 0 && <span className="text-secondary small">/ 10</span>}
    </span>
  );
};

const MovieCard = ({ movie }: { movie: Movie }) => (
  <div className="col-sm-6 col-md-4 col-lg-3 col-xl-2">
    <div className="card card-sm h-100">
      <Link to={`/movies/${movie.id}`} className="d-block">
        <div
          className="card-img-top"
          style={{
            backgroundImage: `url(${movie.posterUrl || "https://via.placeholder.com/300x450?text=No+Poster"})`,
            backgroundSize: "cover",
            backgroundPosition: "center",
            height: 320,
          }}
        />
      </Link>
      <div className="card-body d-flex flex-column">
        <h3 className="card-title mb-1" style={{ fontSize: "0.9rem" }}>
          <Link to={`/movies/${movie.id}`} className="text-reset">
            {movie.title}
          </Link>
        </h3>
        <div className="text-secondary small mb-1">
          {movie.releaseDate ? new Date(movie.releaseDate).getFullYear() : "N/A"}
          {movie.duration ? ` · ${movie.duration} min` : ""}
        </div>
        <div className="d-flex flex-wrap gap-1 mb-2">
          {movie.genres?.slice(0, 2).map((g) => (
            <span key={g} className="badge bg-blue-lt" style={{ fontSize: "0.7rem" }}>
              {g}
            </span>
          ))}
        </div>
        <div className="mt-auto">
          <StarRating rating={movie.averageUserRating} />
        </div>
      </div>
    </div>
  </div>
);

const HomeView = () => {
  const [featured, setFeatured] = useState<Movie[]>([]);
  const [latest, setLatest] = useState<Movie[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([api.get("/movies/featured"), api.get("/movies/latest")])
      .then(([featuredRes, latestRes]) => {
        setFeatured(featuredRes.data);
        setLatest(latestRes.data);
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return (
      <div className="container-xl py-4">
        <div className="text-center py-5">
          <div className="spinner-border text-primary" />
          <div className="mt-2 text-secondary">Loading movies...</div>
        </div>
      </div>
    );
  }

  const hero = featured[0];

  return (
    <div className="container-xl py-4">
      {/* Hero */}
      {hero && (
        <div className="card mb-4 bg-primary-lt border-0">
          <div className="row g-0">
            <div className="col-md-4">
              <div
                className="h-100"
                style={{
                  backgroundImage: `url(${hero.posterUrl || "https://via.placeholder.com/300x450"})`,
                  backgroundSize: "cover",
                  backgroundPosition: "center",
                  minHeight: 300,
                  borderRadius: "var(--tblr-card-border-radius) 0 0 var(--tblr-card-border-radius)",
                }}
              />
            </div>
            <div className="col-md-8">
              <div className="card-body d-flex flex-column justify-content-center h-100 p-4">
                <div className="mb-2">
                  <span className="badge bg-yellow text-dark">Top Rated</span>
                </div>
                <h1 className="mb-2">{hero.title}</h1>
                <div className="d-flex gap-3 mb-3 text-secondary">
                  <span>
                    <i className="ti ti-calendar me-1" />
                    {hero.releaseDate ? new Date(hero.releaseDate).getFullYear() : "N/A"}
                  </span>
                  <span>
                    <i className="ti ti-clock me-1" />
                    {hero.duration} min
                  </span>
                  <span>
                    <i className="ti ti-message-circle me-1" />
                    {hero.reviewCount} reviews
                  </span>
                </div>
                <div className="mb-3">
                  <div className="d-flex gap-3">
                    <div>
                      <div className="text-secondary small">User Score</div>
                      <StarRating rating={hero.averageUserRating} />
                    </div>
                    <div>
                      <div className="text-secondary small">Critic Score</div>
                      <StarRating rating={hero.averageCriticRating} />
                    </div>
                  </div>
                </div>
                <div className="d-flex flex-wrap gap-2 mb-3">
                  {hero.genres?.map((g) => (
                    <span key={g} className="badge bg-blue-lt">
                      {g}
                    </span>
                  ))}
                </div>
                <div>
                  <Link to={`/movies/${hero.id}`} className="btn btn-primary">
                    <i className="ti ti-eye me-1" /> View Details
                  </Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Top Rated */}
      <div className="mb-4">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h2 className="mb-0">
            <i className="ti ti-trophy text-yellow me-2" />
            Top Rated
          </h2>
          <Link to="/movies?sort=averageUserRating,desc" className="btn btn-ghost-primary btn-sm">
            View All <i className="ti ti-arrow-right ms-1" />
          </Link>
        </div>
        <div className="row g-3">
          {featured.slice(0, 6).map((m) => (
            <MovieCard key={m.id} movie={m} />
          ))}
        </div>
      </div>

      {/* Latest */}
      <div className="mb-4">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h2 className="mb-0">
            <i className="ti ti-clock text-blue me-2" />
            Latest Added
          </h2>
          <Link to="/movies?sort=createdAt,desc" className="btn btn-ghost-primary btn-sm">
            View All <i className="ti ti-arrow-right ms-1" />
          </Link>
        </div>
        <div className="row g-3">
          {latest.slice(0, 6).map((m) => (
            <MovieCard key={m.id} movie={m} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default HomeView;
