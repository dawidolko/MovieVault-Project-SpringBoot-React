import { useEffect, useState } from "react";
import { api } from "../services/api";
import { Link } from "react-router";
import { useNotification } from "../contexts/notification";

interface WatchlistItem {
  id: number;
  movieId: number;
  movieTitle: string;
  posterUrl: string;
  averageUserRating: number;
  addedAt: string;
}

const WatchlistView = () => {
  const [items, setItems] = useState<WatchlistItem[]>([]);
  const [loading, setLoading] = useState(true);
  const { showNotification } = useNotification();

  const fetchWatchlist = () => {
    setLoading(true);
    api
      .get("/watchlist")
      .then((res) => setItems(res.data))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchWatchlist();
  }, []);

  const removeItem = async (movieId: number) => {
    try {
      await api.delete(`/watchlist/${movieId}`);
      setItems((prev) => prev.filter((i) => i.movieId !== movieId));
      showNotification("info", "Removed from watchlist.");
    } catch {
      showNotification("error", "Failed to remove from watchlist.");
    }
  };

  if (loading) {
    return (
      <div className="container-xl py-4 text-center">
        <div className="spinner-border text-primary" />
      </div>
    );
  }

  return (
    <div className="container-xl py-4">
      <div className="page-header mb-4">
        <h2 className="page-title">
          <i className="ti ti-bookmark me-2" />
          My Watchlist
        </h2>
      </div>

      {items.length === 0 ? (
        <div className="empty">
          <div className="empty-icon"><i className="ti ti-bookmark-off" style={{ fontSize: 48 }} /></div>
          <p className="empty-title">Your watchlist is empty</p>
          <p className="empty-subtitle text-secondary">Browse movies and add them to your watchlist.</p>
          <div className="empty-action">
            <Link to="/movies" className="btn btn-primary">
              <i className="ti ti-movie me-2" /> Browse Movies
            </Link>
          </div>
        </div>
      ) : (
        <div className="row g-3">
          {items.map((item) => (
            <div key={item.id} className="col-sm-6 col-md-4 col-lg-3 col-xl-2">
              <div className="card card-sm h-100">
                <Link to={`/movies/${item.movieId}`} className="d-block">
                  <div
                    className="card-img-top"
                    style={{
                      backgroundImage: `url(${item.posterUrl || "https://via.placeholder.com/300x450?text=No+Poster"})`,
                      backgroundSize: "cover",
                      backgroundPosition: "center",
                      height: 320,
                    }}
                  />
                </Link>
                <div className="card-body d-flex flex-column">
                  <h3 className="card-title mb-1" style={{ fontSize: "0.9rem" }}>
                    <Link to={`/movies/${item.movieId}`} className="text-reset">{item.movieTitle}</Link>
                  </h3>
                  <div className="d-flex align-items-center gap-1 mb-2">
                    <i className="ti ti-star-filled text-warning" />
                    <strong>{item.averageUserRating > 0 ? item.averageUserRating.toFixed(1) : "N/A"}</strong>
                  </div>
                  <div className="text-secondary small mb-2">
                    Added: {new Date(item.addedAt).toLocaleDateString()}
                  </div>
                  <button className="btn btn-outline-danger btn-sm mt-auto" onClick={() => removeItem(item.movieId)}>
                    <i className="ti ti-trash me-1" /> Remove
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default WatchlistView;
