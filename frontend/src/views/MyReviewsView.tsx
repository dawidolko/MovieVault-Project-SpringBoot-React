import { useEffect, useState } from "react";
import { api } from "../services/api";
import { useUser } from "../contexts/user";
import { Link } from "react-router";
import { useNotification } from "../contexts/notification";

interface Review {
  id: number;
  movieId: number;
  movieTitle: string;
  rating: number;
  title: string;
  content: string;
  createdAt: string;
  isCriticReview: boolean;
  likesCount: number;
}

const MyReviewsView = () => {
  const [reviews, setReviews] = useState<Review[]>([]);
  const [loading, setLoading] = useState(true);
  const { userId } = useUser();
  const { showNotification } = useNotification();

  const fetchReviews = () => {
    if (userId === null || userId === undefined) return;
    setLoading(true);
    api
      .get(`/reviews/user/${userId}`)
      .then((res) => setReviews(res.data))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    if (userId !== null && userId !== undefined) {
      fetchReviews();
    }
  }, [userId]);

  const deleteReview = async (reviewId: number) => {
    try {
      await api.delete(`/reviews/${reviewId}`);
      setReviews((prev) => prev.filter((r) => r.id !== reviewId));
      showNotification("success", "Review deleted.");
    } catch {
      showNotification("error", "Failed to delete review.");
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
          <i className="ti ti-star me-2" />
          My Reviews ({reviews.length})
        </h2>
      </div>

      {reviews.length === 0 ? (
        <div className="empty">
          <div className="empty-icon"><i className="ti ti-star-off" style={{ fontSize: 48 }} /></div>
          <p className="empty-title">No reviews yet</p>
          <p className="empty-subtitle text-secondary">Watch some movies and share your thoughts!</p>
          <div className="empty-action">
            <Link to="/movies" className="btn btn-primary">
              <i className="ti ti-movie me-2" /> Browse Movies
            </Link>
          </div>
        </div>
      ) : (
        <div className="row g-3">
          {reviews.map((review) => (
            <div key={review.id} className="col-md-6 col-lg-4">
              <div className="card h-100">
                <div className="card-body">
                  <div className="d-flex justify-content-between align-items-start mb-2">
                    <Link to={`/movies/${review.movieId}`} className="h3 mb-0 text-reset">
                      {review.movieTitle}
                    </Link>
                    <span className="d-inline-flex align-items-center gap-1">
                      <i className="ti ti-star-filled text-warning" />
                      <strong>{review.rating}</strong>/10
                    </span>
                  </div>
                  {review.title && <div className="fw-bold mb-1">{review.title}</div>}
                  <p className="text-secondary mb-2">{review.content}</p>
                  <div className="d-flex justify-content-between align-items-center">
                    <span className="text-secondary small">
                      {new Date(review.createdAt).toLocaleDateString()}
                      <span className="ms-2"><i className="ti ti-heart me-1" />{review.likesCount}</span>
                    </span>
                    <button className="btn btn-ghost-danger btn-sm" onClick={() => deleteReview(review.id)}>
                      <i className="ti ti-trash me-1" /> Delete
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default MyReviewsView;
