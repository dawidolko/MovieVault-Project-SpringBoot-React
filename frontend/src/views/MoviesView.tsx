import { useEffect, useState } from "react";
import { api } from "../services/api";
import { Link, useSearchParams } from "react-router";

interface Movie {
  id: number;
  title: string;
  posterUrl: string;
  releaseDate: string;
  averageUserRating: number;
  genres: string[];
  duration: number;
  reviewCount: number;
}

interface Genre {
  id: number;
  name: string;
}

interface PageResponse {
  content: Movie[];
  totalPages: number;
  totalElements: number;
  number: number;
}

const MoviesView = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const [movies, setMovies] = useState<Movie[]>([]);
  const [genres, setGenres] = useState<Genre[]>([]);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);

  const page = parseInt(searchParams.get("page") || "0");
  const search = searchParams.get("search") || "";
  const genreId = searchParams.get("genreId") || "";
  const sort = searchParams.get("sort") || "createdAt,desc";

  useEffect(() => {
    api.get("/genres").then((res) => setGenres(res.data));
  }, []);

  useEffect(() => {
    setLoading(true);
    const params: any = { page, size: 12, sort };
    if (search) params.search = search;
    if (genreId) params.genreId = genreId;

    api
      .get("/movies", params)
      .then((res) => {
        const data: PageResponse = res.data;
        setMovies(data.content);
        setTotalPages(data.totalPages);
      })
      .finally(() => setLoading(false));
  }, [page, search, genreId, sort]);

  const updateParam = (key: string, value: string) => {
    const p = new URLSearchParams(searchParams);
    if (value) {
      p.set(key, value);
    } else {
      p.delete(key);
    }
    if (key !== "page") p.set("page", "0");
    setSearchParams(p);
  };

  return (
    <div className="container-xl py-4">
      <div className="page-header mb-4">
        <div className="row align-items-center">
          <div className="col-auto">
            <h2 className="page-title">
              <i className="ti ti-movie me-2" />
              Movies
            </h2>
          </div>
        </div>
      </div>

      {/* Filters */}
      <div className="card mb-4">
        <div className="card-body">
          <div className="row g-3">
            <div className="col-md-5">
              <div className="input-icon">
                <span className="input-icon-addon"><i className="ti ti-search" /></span>
                <input
                  type="text"
                  className="form-control"
                  placeholder="Search movies..."
                  value={search}
                  onChange={(e) => updateParam("search", e.target.value)}
                />
              </div>
            </div>
            <div className="col-md-3">
              <select className="form-select" value={genreId} onChange={(e) => updateParam("genreId", e.target.value)}>
                <option value="">All Genres</option>
                {genres.map((g) => (
                  <option key={g.id} value={g.id}>
                    {g.name}
                  </option>
                ))}
              </select>
            </div>
            <div className="col-md-4">
              <select className="form-select" value={sort} onChange={(e) => updateParam("sort", e.target.value)}>
                <option value="createdAt,desc">Newest First</option>
                <option value="averageUserRating,desc">Highest Rated</option>
                <option value="title,asc">Title A-Z</option>
                <option value="title,desc">Title Z-A</option>
                <option value="releaseDate,desc">Release Date (Newest)</option>
                <option value="releaseDate,asc">Release Date (Oldest)</option>
              </select>
            </div>
          </div>
        </div>
      </div>

      {loading ? (
        <div className="text-center py-5">
          <div className="spinner-border text-primary" />
        </div>
      ) : movies.length === 0 ? (
        <div className="empty">
          <div className="empty-icon"><i className="ti ti-movie-off" style={{ fontSize: 48 }} /></div>
          <p className="empty-title">No movies found</p>
          <p className="empty-subtitle text-secondary">Try adjusting your search or filters.</p>
        </div>
      ) : (
        <>
          <div className="row g-3">
            {movies.map((movie) => (
              <div key={movie.id} className="col-sm-6 col-md-4 col-lg-3 col-xl-2">
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
                      <Link to={`/movies/${movie.id}`} className="text-reset">{movie.title}</Link>
                    </h3>
                    <div className="text-secondary small mb-1">
                      {movie.releaseDate ? new Date(movie.releaseDate).getFullYear() : "N/A"}
                      {movie.duration ? ` · ${movie.duration} min` : ""}
                    </div>
                    <div className="d-flex flex-wrap gap-1 mb-2">
                      {movie.genres?.slice(0, 2).map((g) => (
                        <span key={g} className="badge bg-blue-lt" style={{ fontSize: "0.7rem" }}>{g}</span>
                      ))}
                    </div>
                    <div className="mt-auto d-flex align-items-center gap-1">
                      <i className="ti ti-star-filled text-warning" />
                      <strong>{movie.averageUserRating > 0 ? movie.averageUserRating.toFixed(1) : "N/A"}</strong>
                      <span className="text-secondary small ms-auto">
                        <i className="ti ti-message-circle me-1" />{movie.reviewCount}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>

          {/* Pagination */}
          {totalPages > 1 && (
            <div className="d-flex justify-content-center mt-4">
              <ul className="pagination">
                <li className={`page-item ${page === 0 ? "disabled" : ""}`}>
                  <button className="page-link" onClick={() => updateParam("page", String(page - 1))}>
                    <i className="ti ti-chevron-left" />
                  </button>
                </li>
                {Array.from({ length: Math.min(totalPages, 7) }, (_, i) => {
                  let pageNum = i;
                  if (totalPages > 7) {
                    const start = Math.max(0, Math.min(page - 3, totalPages - 7));
                    pageNum = start + i;
                  }
                  return (
                    <li key={pageNum} className={`page-item ${pageNum === page ? "active" : ""}`}>
                      <button className="page-link" onClick={() => updateParam("page", String(pageNum))}>
                        {pageNum + 1}
                      </button>
                    </li>
                  );
                })}
                <li className={`page-item ${page >= totalPages - 1 ? "disabled" : ""}`}>
                  <button className="page-link" onClick={() => updateParam("page", String(page + 1))}>
                    <i className="ti ti-chevron-right" />
                  </button>
                </li>
              </ul>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default MoviesView;
