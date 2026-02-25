import { useEffect, useState } from "react";
import { api } from "../../services/api";
import { Link } from "react-router";

const HomePage = () => {
  const [featured, setFeatured] = useState<any[]>([]);
  const [latest, setLatest] = useState<any[]>([]);

  useEffect(() => {
    api.get("/movies/featured").then((r) => setFeatured(r.data)).catch(() => {});
    api.get("/movies/latest").then((r) => setLatest(r.data)).catch(() => {});
  }, []);

  const MovieCard = ({ movie }: { movie: any }) => (
    <div className="col-sm-6 col-lg-3">
      <div className="card card-sm">
        <div className="card-body">
          <div className="d-flex align-items-center">
            <div>
              <div className="font-weight-medium">
                <Link to={`/movies/${movie.id}`} className="text-reset">{movie.title}</Link>
              </div>
              <div className="text-secondary">
                {movie.releaseDate?.substring(0, 4)} &middot; {movie.duration} min
              </div>
              <div className="mt-1">
                {movie.genres?.map((g: string) => (
                  <span key={g} className="badge bg-blue-lt me-1">{g}</span>
                ))}
              </div>
            </div>
            <div className="ms-auto">
              <span className="badge bg-yellow-lt fs-4">
                <i className="ti ti-star-filled me-1" />
                {movie.averageUserRating?.toFixed(1) || "N/A"}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );

  return (
    <div className="page-wrapper">
      <div className="page-header d-print-none">
        <div className="container-xl">
          <div className="page-pretitle">Welcome to</div>
          <h2 className="page-title">MovieVault</h2>
        </div>
      </div>
      <div className="page-body">
        <div className="container-xl">
          <h3 className="mb-3">Top Rated Movies</h3>
          <div className="row row-cards mb-4">
            {featured.slice(0, 8).map((m) => <MovieCard key={m.id} movie={m} />)}
            {featured.length === 0 && <div className="text-secondary">Loading movies...</div>}
          </div>

          <h3 className="mb-3">Recently Added</h3>
          <div className="row row-cards">
            {latest.slice(0, 8).map((m) => <MovieCard key={m.id} movie={m} />)}
          </div>

          <div className="text-center mt-4">
            <Link to="/movies" className="btn btn-primary">Browse All Movies</Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
