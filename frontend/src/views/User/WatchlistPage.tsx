import { useEffect, useState } from "react";
import { api } from "../../services/api";
import { Link } from "react-router";
import { useNotification } from "../../contexts/notification";

const WatchlistPage = () => {
  const [items, setItems] = useState<any[]>([]);
  const { showNotification } = useNotification();

  const load = () => { api.get("/watchlist").then((r) => setItems(r.data)).catch(() => {}); };
  useEffect(() => { load(); }, []);

  const remove = async (movieId: number) => {
    await api.delete(`/watchlist/${movieId}`);
    showNotification("info", "Removed from watchlist.");
    load();
  };

  return (
    <div className="page-wrapper">
      <div className="page-header d-print-none"><div className="container-xl"><h2 className="page-title">My Watchlist</h2></div></div>
      <div className="page-body">
        <div className="container-xl">
          {items.length === 0 && <p className="text-secondary">Your watchlist is empty.</p>}
          <div className="row row-cards">
            {items.map((w) => (
              <div key={w.id} className="col-sm-6 col-lg-3">
                <div className="card">
                  <div className="card-body">
                    <h3 className="card-title"><Link to={`/movies/${w.movieId}`} className="text-reset">{w.movieTitle}</Link></h3>
                    <div className="text-secondary mb-2"><i className="ti ti-star-filled text-yellow me-1" />{w.averageUserRating?.toFixed(1) || "-"}</div>
                    <button className="btn btn-sm btn-outline-danger" onClick={() => remove(w.movieId)}>Remove</button>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default WatchlistPage;
