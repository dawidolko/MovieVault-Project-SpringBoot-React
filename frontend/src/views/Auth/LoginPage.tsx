import { useState } from "react";
import { useAuth } from "../../contexts/auth";
import { useUser } from "../../contexts/user";
import { api } from "../../services/api";
import { Link, useNavigate } from "react-router";
import { useNotification } from "../../contexts/notification";

const LoginPage = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const { login } = useAuth();
  const { fetchUser } = useUser();
  const navigate = useNavigate();
  const { showNotification } = useNotification();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    try {
      const res = await api.post("/auth/login", { email, password });
      login({ email, token: res.data.token });
      setTimeout(() => {
        fetchUser();
        navigate("/", { replace: true });
        showNotification("success", "Successfully logged in!");
      }, 100);
    } catch {
      setError("Invalid email or password.");
    }
  };

  return (
    <div className="page page-center">
      <div className="container container-tight py-4">
        <div className="text-center mb-4">
          <img src="/movievault.svg" height="48" alt="MovieVault" />
          <h2 className="mt-3">MovieVault</h2>
        </div>
        <div className="card card-md">
          <div className="card-body">
            <h2 className="h2 text-center mb-4">Login to your account</h2>
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label">Email address</label>
                <input type="email" className="form-control" value={email} onChange={(e) => setEmail(e.target.value)} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Password</label>
                <input type="password" className="form-control" value={password} onChange={(e) => setPassword(e.target.value)} required />
              </div>
              <div className="form-footer">
                <button type="submit" className="btn btn-primary w-100">Sign in</button>
              </div>
            </form>
          </div>
        </div>
        <div className="text-center text-secondary mt-3">
          Don't have an account? <Link to="/register">Register</Link>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
