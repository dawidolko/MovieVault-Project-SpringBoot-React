import { useEffect, useState } from "react";
import { api } from "../../services/api";
import { Link, useSearchParams } from "react-router";

const MovieListPage = () => {
  const [movies, setMovies] = useState<any>({ content: [], totalPages: 0 });
  const [genres, setGenres] = useState<any[]>([]);
  const [search, setSearch] = useState("");
  const [genreId, setGenreId] = useState<string>("");
  const [searchParams, setSearchParams] = useSearchParams();
  const page = parseInt(searchParams.get("page") || "0");

  useEffect(() => {
    api.get("/genres").then((r) => setGenres(r.data)).catch(() => {});
  }, []);

  useEffect(() => {
    const params: any = { page, size: 12, sort: "title,asc" };
    if (search) params.search = search;
    if (genreId) params.genreId = genreId;
    api.get("/movies", params).then((r) => setMovies(r.data)).catch(() => {});
  }, [page, search, genreId]);

  return (
    <div className="page-wrapper">
      <div className="page-header d-print-none">
        <div className="container-xl">
          <h2 className="page-title">Movies</h2>
        </div>
      </div>
      <div className="page-body">
        <div className="container-xl">
          <div className="row mb-3">
            <div className="col-md-4">
              <input type="text" className="form-control" placeholder="Search movies..." value={search}
                onChange={(e) => { setSearch(e.target.value); setSearchParams({ page: "0" }); }} />
            </div>
            <div className="col-md-3">
              <select className="form-select" value={genreId}
                onChange={(e) => { setGenreId(e.target.value); setSearchParams({ page: "0" }); }}>
                <option value="">All genres</option>
                {genres.map((g: any) => <option key={g.id} value={g.id}>{g.name}</option>)}
              </select>
            </div>
          </div>

          <div className="row row-cards">
            {movies.content?.map((m: any) => (
              <div key={m.id} className="col-sm-6 col-lg-3">
                <div className="card">
                  <div className="card-body">
                    <h3 className="card-title">
                      <Link to={`/movies/${m.id}`} className="text-reset">{m.title}</Link>
                    </h3>
                    <div className="text-secondary mb-2">
                      {m.releaseDate?.substring(0, 4)} &middot; {m.duration} min
                    </div>
                    <div className="mb-2">
                      {m.genres?.map((g: string) => <span key={g} className="badge bg-blue-lt me-1">{g}</span>)}
                    </div>
                    <div className="d-flex justify-content-between">
                      <span><i className="ti ti-star-filled text-yellow me-1" />{m.averageUserRating?.toFixed(1) || "-"} User</span>
                      <span><i className="ti ti-award text-red me-1" />{m.averageCriticRating?.toFixed(1) || "-"} Critic</span>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>

          {movies.totalPages > 1 && (
            <div className="d-flex justify-content-center mt-4">
              <ul className="pagination">
                {Array.from({ length: movies.totalPages }, (_, i) => (
                  <li key={i} className={`page-item ${i === page ? "active" : ""}`}>
                    <a className="page-link" href="#" onClick={(e) => { e.preventDefault(); setSearchParams({ page: String(i) }); }}>
                      {i + 1}
                    </a>
                  </li>
                ))}
              </ul>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default MovieListPage;
