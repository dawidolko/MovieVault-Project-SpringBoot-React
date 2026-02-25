import { useEffect, useState } from "react";
import { useParams, Link } from "react-router";
import { api } from "../../services/api";
import { useAuth } from "../../contexts/auth";
import { useNotification } from "../../contexts/notification";

const MovieDetailPage = () => {
  const { id } = useParams();
  const [movie, setMovie] = useState<any>(null);
  const [reviews, setReviews] = useState<any[]>([]);
  const [reviewForm, setReviewForm] = useState({ rating: 8, title: "", content: "" });
  const { state } = useAuth();
  const { showNotification } = useNotification();

  useEffect(() => {
    api.get(`/movies/${id}`).then((r) => setMovie(r.data)).catch(() => {});
    api.get(`/reviews/movie/${id}`).then((r) => setReviews(r.data)).catch(() => {});
  }, [id]);

  const submitReview = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.post("/reviews", { movieId: Number(id), ...reviewForm });
      showNotification("success", "Review submitted!");
      api.get(`/reviews/movie/${id}`).then((r) => setReviews(r.data));
      api.get(`/movies/${id}`).then((r) => setMovie(r.data));
      setReviewForm({ rating: 8, title: "", content: "" });
    } catch (err: any) {
      showNotification("error", err.response?.data?.error || "Failed to submit review.");
    }
  };

  const toggleLike = async (reviewId: number) => {
    if (!state.loggedIn) return;
    try {
      await api.post(`/reviews/${reviewId}/like`);
      api.get(`/reviews/movie/${id}`).then((r) => setReviews(r.data));
    } catch {}
  };

  if (!movie) return <div className="page-wrapper"><div className="container-xl py-4">Loading...</div></div>;

  return (
    <div className="page-wrapper">
      <div className="page-body">
        <div className="container-xl">
          <div className="page-header d-print-none mb-4">
            <div className="row align-items-center">
              <div className="col-auto">
                <h2 className="page-title">{movie.title}</h2>
                <div className="text-secondary">
                  {movie.releaseDate?.substring(0, 4)} &middot; {movie.duration} min &middot; {movie.country}
                </div>
              </div>
              <div className="col-auto ms-auto">
                <span className="badge bg-yellow fs-4 me-2">
                  <i className="ti ti-star-filled me-1" />{movie.averageUserRating?.toFixed(1)} User
                </span>
                <span className="badge bg-red-lt fs-4">
                  <i className="ti ti-award me-1" />{movie.averageCriticRating?.toFixed(1)} Critic
                </span>
              </div>
            </div>
          </div>

          <div className="row">
            <div className="col-lg-8">
              <div className="card mb-3">
                <div className="card-body">
                  <h3 className="card-title">About</h3>
                  <p>{movie.description}</p>
                  <div className="mb-2">
                    {movie.genres?.map((g: string) => <span key={g} className="badge bg-blue-lt me-1">{g}</span>)}
                  </div>
                </div>
              </div>

              {movie.directors?.length > 0 && (
                <div className="card mb-3">
                  <div className="card-body">
                    <h3 className="card-title">Directors</h3>
                    {movie.directors.map((d: any) => (
                      <Link key={d.id} to={`/persons/${d.id}`} className="badge bg-purple-lt me-2">{d.firstName} {d.lastName}</Link>
                    ))}
                  </div>
                </div>
              )}

              {movie.cast?.length > 0 && (
                <div className="card mb-3">
                  <div className="card-body">
                    <h3 className="card-title">Cast</h3>
                    <div className="row">
                      {movie.cast.map((c: any) => (
                        <div key={c.personId} className="col-sm-6 col-lg-4 mb-2">
                          <Link to={`/persons/${c.personId}`} className="text-reset fw-bold">{c.firstName} {c.lastName}</Link>
                          <div className="text-secondary small">as {c.characterName}</div>
                        </div>
                      ))}
                    </div>
                  </div>
                </div>
              )}

              <div className="card mb-3">
                <div className="card-header"><h3 className="card-title">Reviews ({reviews.length})</h3></div>
                <div className="card-body">
                  {reviews.map((r: any) => (
                    <div key={r.id} className="mb-3 pb-3 border-bottom">
                      <div className="d-flex justify-content-between align-items-center mb-1">
                        <div>
                          <strong>{r.userFirstName} {r.userLastName}</strong>
                          {r.isCriticReview && <span className="badge bg-red-lt ms-2">Critic</span>}
                        </div>
                        <span className="badge bg-yellow-lt"><i className="ti ti-star-filled me-1" />{r.rating}/10</span>
                      </div>
                      {r.title && <div className="fw-bold">{r.title}</div>}
                      <p className="text-secondary mb-1">{r.content}</p>
                      <button className="btn btn-sm btn-ghost-primary" onClick={() => toggleLike(r.id)}>
                        <i className={`ti ti-heart${r.likedByCurrentUser ? "-filled" : ""} me-1`} />{r.likesCount}
                      </button>
                    </div>
                  ))}
                  {reviews.length === 0 && <p className="text-secondary">No reviews yet. Be the first!</p>}
                </div>
              </div>

              {state.loggedIn && (
                <div className="card mb-3">
                  <div className="card-header"><h3 className="card-title">Write a Review</h3></div>
                  <div className="card-body">
                    <form onSubmit={submitReview}>
                      <div className="row mb-3">
                        <div className="col-md-3">
                          <label className="form-label">Rating (1-10)</label>
                          <input type="number" className="form-control" min="1" max="10" value={reviewForm.rating}
                            onChange={(e) => setReviewForm({ ...reviewForm, rating: Number(e.target.value) })} />
                        </div>
                        <div className="col-md-9">
                          <label className="form-label">Title</label>
                          <input type="text" className="form-control" value={reviewForm.title}
                            onChange={(e) => setReviewForm({ ...reviewForm, title: e.target.value })} />
                        </div>
                      </div>
                      <div className="mb-3">
                        <label className="form-label">Your review</label>
                        <textarea className="form-control" rows={3} value={reviewForm.content}
                          onChange={(e) => setReviewForm({ ...reviewForm, content: e.target.value })} required />
                      </div>
                      <button type="submit" className="btn btn-primary">Submit Review</button>
                    </form>
                  </div>
                </div>
              )}
            </div>

            <div className="col-lg-4">
              <div className="card">
                <div className="card-body">
                  <h3 className="card-title">Details</h3>
                  <div className="datagrid">
                    <div className="datagrid-item"><div className="datagrid-title">Language</div><div className="datagrid-content">{movie.language || "-"}</div></div>
                    <div className="datagrid-item"><div className="datagrid-title">Country</div><div className="datagrid-content">{movie.country || "-"}</div></div>
                    <div className="datagrid-item"><div className="datagrid-title">Reviews</div><div className="datagrid-content">{movie.reviewCount || 0}</div></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MovieDetailPage;
