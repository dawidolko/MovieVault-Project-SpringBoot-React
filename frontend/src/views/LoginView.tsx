import { useState } from "react";
import { api } from "../services/api";
import { useAuth } from "../contexts/auth";
import { useNavigate, Link } from "react-router";
import { useNotification } from "../contexts/notification";

const LoginView = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();
  const { showNotification } = useNotification();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      const res = await api.post("/auth/login", { email, password });
      login({ email: res.data.user.email, token: res.data.token });
      showNotification("success", "Successfully logged in!");
      navigate("/", { replace: true });
    } catch {
      showNotification("error", "Invalid email or password.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="page page-center">
      <div className="container container-tight py-4">
        <div className="text-center mb-4">
          <Link to="/" className="navbar-brand navbar-brand-autodark">
            <h1 className="mb-0">
              <i className="ti ti-movie text-primary me-2" />
              MovieVault
            </h1>
          </Link>
        </div>
        <div className="card card-md">
          <div className="card-body">
            <h2 className="h2 text-center mb-4">Login to your account</h2>
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Email address</label>
                <input
                  type="email"
                  className="form-control"
                  placeholder="your@email.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
              </div>
              <div className="mb-3">
                <label className="form-label">Password</label>
                <input
                  type="password"
                  className="form-control"
                  placeholder="Your password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </div>
              <div className="form-footer">
                <button type="submit" className="btn btn-primary w-100" disabled={loading}>
                  {loading ? (
                    <span className="spinner-border spinner-border-sm me-2" />
                  ) : (
                    <i className="ti ti-login me-2" />
                  )}
                  Sign in
                </button>
              </div>
            </form>
          </div>
        </div>
        <div className="text-center text-secondary mt-3">
          Don't have an account? <Link to="/register">Sign up</Link>
        </div>
        <div className="text-center text-secondary mt-2 small">
          <strong>Demo accounts:</strong> admin@movievault.com / admin123 | jan@movievault.com / user123
        </div>
      </div>
    </div>
  );
};

export default LoginView;
