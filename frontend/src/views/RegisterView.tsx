import { useState } from "react";
import { api } from "../services/api";
import { useAuth } from "../contexts/auth";
import { useNavigate, Link } from "react-router";
import { useNotification } from "../contexts/notification";

const RegisterView = () => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
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
      const res = await api.post("/auth/register", { firstName, lastName, email, password });
      login({ email: res.data.user.email, token: res.data.token });
      showNotification("success", "Account created successfully!");
      navigate("/", { replace: true });
    } catch (err: any) {
      const msg = err.response?.data?.error || "Registration failed.";
      showNotification("error", msg);
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
            <h2 className="h2 text-center mb-4">Create new account</h2>
            <form onSubmit={handleSubmit}>
              <div className="row mb-3">
                <div className="col">
                  <label className="form-label">First name</label>
                  <input type="text" className="form-control" value={firstName} onChange={(e) => setFirstName(e.target.value)} required />
                </div>
                <div className="col">
                  <label className="form-label">Last name</label>
                  <input type="text" className="form-control" value={lastName} onChange={(e) => setLastName(e.target.value)} required />
                </div>
              </div>
              <div className="mb-3">
                <label className="form-label">Email address</label>
                <input type="email" className="form-control" placeholder="your@email.com" value={email} onChange={(e) => setEmail(e.target.value)} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Password</label>
                <input type="password" className="form-control" placeholder="Min 6 characters" value={password} onChange={(e) => setPassword(e.target.value)} required minLength={6} />
              </div>
              <div className="form-footer">
                <button type="submit" className="btn btn-primary w-100" disabled={loading}>
                  {loading ? <span className="spinner-border spinner-border-sm me-2" /> : <i className="ti ti-user-plus me-2" />}
                  Create account
                </button>
              </div>
            </form>
          </div>
        </div>
        <div className="text-center text-secondary mt-3">
          Already have an account? <Link to="/login">Sign in</Link>
        </div>
      </div>
    </div>
  );
};

export default RegisterView;
