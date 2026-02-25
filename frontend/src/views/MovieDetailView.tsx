import { useEffect, useState } from "react";
import { useParams, Link } from "react-router";
import { api } from "../services/api";
import { useAuth } from "../contexts/auth";
import { useNotification } from "../contexts/notification";

interface CastMember {
  personId: number;
  firstName: string;
  lastName: string;
  photoUrl: string;
  characterName: string;
  castOrder: number;
}

interface Director {
  id: number;
  firstName: string;
  lastName: string;
}

interface Movie {
  id: number;
  title: string;
  originalTitle: string;
  description: string;
  releaseDate: string;
  duration: number;
  posterUrl: string;
  trailerUrl: string;
  country: string;
  language: string;
  budget: number;
  boxOffice: number;
  averageUserRating: number;
  averageCriticRating: number;
  genres: string[];
  cast: CastMember[];
  directors: Director[];
  reviewCount: number;
}

interface Review {
  id: number;
  userId: number;
  userFirstName: string;
  userLastName: string;
  userAvatarUrl: string;
  rating: number;
  title: string;
  content: string;
  createdAt: string;
  isCriticReview: boolean;
  likesCount: number;
  likedByCurrentUser: boolean;
}

const MovieDetailView = () => {
  const { id } = useParams();
  const [movie, setMovie] = useState<Movie | null>(null);
  const [reviews, setReviews] = useState<Review[]>([]);
  const [loading, setLoading] = useState(true);
  const [inWatchlist, setInWatchlist] = useState(false);
  const [reviewForm, setReviewForm] = useState({ rating: 8, title: "", content: "" });
  const [submitting, setSubmitting] = useState(false);
  const { state } = useAuth();
  const { showNotification } = useNotification();

  const fetchData = () => {
    setLoading(true);
    Promise.all([
      api.get(`/movies/${id}`),
      api.get(`/reviews/movie/${id}`),
      state.loggedIn ? api.get(`/watchlist/check/${id}`).catch(() => ({ data: { inWatchlist: false } })) : Promise.resolve({ data: { inWatchlist: false } }),
    ])
      .then(([movieRes, reviewsRes, watchlistRes]) => {
        setMovie(movieRes.data);
        setReviews(reviewsRes.data);
        setInWatchlist(watchlistRes.data.inWatchlist);
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchData();
  }, [id]);

  const toggleWatchlist = async () => {
    if (!state.loggedIn) {
      showNotification("warning", "Please login to manage your watchlist.");
      return;
    }
    try {
      if (inWatchlist) {
        await api.delete(`/watchlist/${id}`);
        setInWatchlist(false);
        showNotification("info", "Removed from watchlist.");
      } else {
        await api.post(`/watchlist/${id}`);
        setInWatchlist(true);
        showNotification("success", "Added to watchlist!");
      }
    } catch {
      showNotification("error", "Failed to update watchlist.");
    }
  };

  const submitReview = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!state.loggedIn) {
      showNotification("warning", "Please login to submit a review.");
      return;
    }
    setSubmitting(true);
    try {
      await api.post("/reviews", { movieId: Number(id), ...reviewForm });
      showNotification("success", "Review submitted!");
      setReviewForm({ rating: 8, title: "", content: "" });
      fetchData();
    } catch (err: any) {
      showNotification("error", err.response?.data?.error || "Failed to submit review.");
    } finally {
      setSubmitting(false);
    }
  };

  const toggleLike = async (reviewId: number) => {
    if (!state.loggedIn) return;
    try {
      await api.post(`/reviews/${reviewId}/like`);
      fetchData();
    } catch {}
  };

  if (loading) {
    return (
      <div className="container-xl py-4 text-center">
        <div className="spinner-border text-primary" />
      </div>
    );
  }

  if (!movie) {
    return (
      <div className="container-xl py-4">
        <div className="empty">
          <p className="empty-title">Movie not found</p>
          <Link to="/movies" className="btn btn-primary">Back to Movies</Link>
        </div>
      </div>
    );
  }

  return (
    <div className="container-xl py-4">
      {/* Movie Header */}
      <div className="card mb-4">
        <div className="row g-0">
          <div className="col-md-3">
            <div
              className="h-100"
              style={{
                backgroundImage: `url(${movie.posterUrl || "https://via.placeholder.com/300x450?text=No+Poster"})`,
                backgroundSize: "cover",
                backgroundPosition: "center",
                minHeight: 400,
                borderRadius: "var(--tblr-card-border-radius) 0 0 var(--tblr-card-border-radius)",
              }}
            />
          </div>
          <div className="col-md-9">
            <div className="card-body p-4">
              <div className="d-flex justify-content-between align-items-start mb-2">
                <div>
                  <h1 className="mb-1">{movie.title}</h1>
                  {movie.originalTitle && movie.originalTitle !== movie.title && (
                    <div className="text-secondary mb-1">{movie.originalTitle}</div>
                  )}
                </div>
                <button
                  className={`btn ${inWatchlist ? "btn-warning" : "btn-outline-warning"}`}
                  onClick={toggleWatchlist}
                >
                  <i className={`ti ${inWatchlist ? "ti-bookmark-filled" : "ti-bookmark"} me-1`} />
                  {inWatchlist ? "In Watchlist" : "Add to Watchlist"}
                </button>
              </div>

              <div className="d-flex flex-wrap gap-2 mb-3">
                {movie.genres?.map((g) => (
                  <span key={g} className="badge bg-blue-lt">{g}</span>
                ))}
              </div>

              <div className="d-flex flex-wrap gap-4 mb-3 text-secondary">
                {movie.releaseDate && (
                  <span><i className="ti ti-calendar me-1" />{new Date(movie.releaseDate).toLocaleDateString()}</span>
                )}
                {movie.duration && (
                  <span><i className="ti ti-clock me-1" />{Math.floor(movie.duration / 60)}h {movie.duration % 60}m</span>
                )}
                {movie.country && <span><i className="ti ti-flag me-1" />{movie.country}</span>}
                {movie.language && <span><i className="ti ti-language me-1" />{movie.language}</span>}
              </div>

              {/* Ratings */}
              <div className="row g-3 mb-3">
                <div className="col-auto">
                  <div className="card card-sm">
                    <div className="card-body p-2 px-3 text-center">
                      <div className="text-secondary small">User Score</div>
                      <div className="d-flex align-items-center gap-1">
                        <i className="ti ti-star-filled text-warning fs-1" />
                        <span className="h2 mb-0">{movie.averageUserRating > 0 ? movie.averageUserRating.toFixed(1) : "N/A"}</span>
                        <span className="text-secondary">/10</span>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="col-auto">
                  <div className="card card-sm">
                    <div className="card-body p-2 px-3 text-center">
                      <div className="text-secondary small">Critic Score</div>
                      <div className="d-flex align-items-center gap-1">
                        <i className="ti ti-rosette text-red fs-1" />
                        <span className="h2 mb-0">{movie.averageCriticRating > 0 ? movie.averageCriticRating.toFixed(1) : "N/A"}</span>
                        <span className="text-secondary">/10</span>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="col-auto">
                  <div className="card card-sm">
                    <div className="card-body p-2 px-3 text-center">
                      <div className="text-secondary small">Reviews</div>
                      <div className="h2 mb-0">{movie.reviewCount}</div>
                    </div>
                  </div>
                </div>
              </div>

              {movie.description && <p className="mb-3">{movie.description}</p>}

              {movie.directors && movie.directors.length > 0 && (
                <div className="mb-2">
                  <strong>Director{movie.directors.length > 1 ? "s" : ""}:</strong>{" "}
                  {movie.directors.map((d) => `${d.firstName} ${d.lastName}`).join(", ")}
                </div>
              )}
            </div>
          </div>
        </div>
      </div>

      {/* Cast */}
      {movie.cast && movie.cast.length > 0 && (
        <div className="card mb-4">
          <div className="card-header">
            <h3 className="card-title"><i className="ti ti-users me-2" />Cast</h3>
          </div>
          <div className="card-body">
            <div className="row g-3">
              {movie.cast.map((c) => (
                <div key={`${c.personId}-${c.castOrder}`} className="col-6 col-md-3 col-lg-2">
                  <div className="text-center">
                    <span
                      className="avatar avatar-xl mb-2"
                      style={c.photoUrl ? { backgroundImage: `url(${c.photoUrl})` } : {}}
                    >
                      {!c.photoUrl && (c.firstName?.[0] || "?")}
                    </span>
                    <div className="fw-bold small">{c.firstName} {c.lastName}</div>
                    <div className="text-secondary small">{c.characterName}</div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      )}

      {/* Reviews */}
      <div className="card mb-4">
        <div className="card-header">
          <h3 className="card-title"><i className="ti ti-message-circle me-2" />Reviews ({reviews.length})</h3>
        </div>
        <div className="card-body">
          {/* Add review form */}
          {state.loggedIn && (
            <form onSubmit={submitReview} className="mb-4 p-3 rounded border">
              <h4 className="mb-3">Write a Review</h4>
              <div className="row g-3 mb-3">
                <div className="col-md-2">
                  <label className="form-label">Rating</label>
                  <select className="form-select" value={reviewForm.rating} onChange={(e) => setReviewForm({ ...reviewForm, rating: Number(e.target.value) })}>
                    {[10, 9, 8, 7, 6, 5, 4, 3, 2, 1].map((n) => (
                      <option key={n} value={n}>{n}/10</option>
                    ))}
                  </select>
                </div>
                <div className="col-md-10">
                  <label className="form-label">Title</label>
                  <input type="text" className="form-control" value={reviewForm.title} onChange={(e) => setReviewForm({ ...reviewForm, title: e.target.value })} required />
                </div>
              </div>
              <div className="mb-3">
                <label className="form-label">Your review</label>
                <textarea className="form-control" rows={3} value={reviewForm.content} onChange={(e) => setReviewForm({ ...reviewForm, content: e.target.value })} required />
              </div>
              <button type="submit" className="btn btn-primary" disabled={submitting}>
                {submitting ? <span className="spinner-border spinner-border-sm me-2" /> : <i className="ti ti-send me-2" />}
                Submit Review
              </button>
            </form>
          )}

          {reviews.length === 0 ? (
            <div className="text-center text-secondary py-3">No reviews yet. Be the first to review!</div>
          ) : (
            <div className="divide-y">
              {reviews.map((review) => (
                <div key={review.id} className="py-3">
                  <div className="d-flex align-items-start gap-3">
                    <span className="avatar avatar-sm" style={review.userAvatarUrl ? { backgroundImage: `url(${review.userAvatarUrl})` } : {}}>
                      {!review.userAvatarUrl && (review.userFirstName?.[0] || "?")}
                    </span>
                    <div className="flex-grow-1">
                      <div className="d-flex align-items-center gap-2 mb-1">
                        <strong>{review.userFirstName} {review.userLastName}</strong>
                        {review.isCriticReview && <span className="badge bg-red-lt">Critic</span>}
                        <span className="d-inline-flex align-items-center gap-1 ms-2">
                          <i className="ti ti-star-filled text-warning" />
                          <strong>{review.rating}</strong>/10
                        </span>
                        <span className="text-secondary small ms-auto">
                          {new Date(review.createdAt).toLocaleDateString()}
                        </span>
                      </div>
                      {review.title && <div className="fw-bold mb-1">{review.title}</div>}
                      <p className="text-secondary mb-2">{review.content}</p>
                      <div className="d-flex gap-2">
                        <button
                          className={`btn btn-sm ${review.likedByCurrentUser ? "btn-red" : "btn-ghost-secondary"}`}
                          onClick={() => toggleLike(review.id)}
                        >
                          <i className={`ti ${review.likedByCurrentUser ? "ti-heart-filled" : "ti-heart"} me-1`} />
                          {review.likesCount}
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default MovieDetailView;
