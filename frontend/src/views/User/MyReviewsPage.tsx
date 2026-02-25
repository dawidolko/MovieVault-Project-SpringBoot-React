import { useEffect, useState } from "react";
import { api } from "../../services/api";
import { useUser } from "../../contexts/user";
import { Link } from "react-router";
import { useNotification } from "../../contexts/notification";

const MyReviewsPage = () => {
  const [reviews, setReviews] = useState<any[]>([]);
  const { userId } = useUser();
  const { showNotification } = useNotification();

  const loadReviews = () => {
    if (userId) api.get(`/reviews/user/${userId}`).then((r) => setReviews(r.data)).catch(() => {});
  };

  useEffect(() => { loadReviews(); }, [userId]);

  const deleteReview = async (id: number) => {
    try {
      await api.delete(`/reviews/${id}`);
      showNotification("success", "Review deleted.");
      loadReviews();
    } catch { showNotification("error", "Failed to delete review."); }
  };

  return (
    <div className="page-wrapper">
      <div className="page-header d-print-none"><div className="container-xl"><h2 className="page-title">My Reviews</h2></div></div>
      <div className="page-body">
        <div className="container-xl">
          {reviews.length === 0 && <p className="text-secondary">You haven't written any reviews yet.</p>}
          {reviews.map((r) => (
            <div key={r.id} className="card mb-3">
              <div className="card-body">
                <div className="d-flex justify-content-between align-items-center">
                  <div>
                    <Link to={`/movies/${r.movieId}`} className="fw-bold text-reset">{r.movieTitle}</Link>
                    <span className="badge bg-yellow-lt ms-2"><i className="ti ti-star-filled me-1" />{r.rating}/10</span>
                  </div>
                  <button className="btn btn-sm btn-outline-danger" onClick={() => deleteReview(r.id)}>Delete</button>
                </div>
                {r.title && <div className="fw-bold mt-2">{r.title}</div>}
                <p className="text-secondary mt-1 mb-0">{r.content}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default MyReviewsPage;
