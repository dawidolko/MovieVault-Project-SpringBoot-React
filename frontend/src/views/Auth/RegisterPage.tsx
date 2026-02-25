import { useState } from "react";
import { useAuth } from "../../contexts/auth";
import { useUser } from "../../contexts/user";
import { api } from "../../services/api";
import { Link, useNavigate } from "react-router";
import { useNotification } from "../../contexts/notification";

const RegisterPage = () => {
  const [form, setForm] = useState({ firstName: "", lastName: "", email: "", password: "" });
  const [error, setError] = useState("");
  const { login } = useAuth();
  const { fetchUser } = useUser();
  const navigate = useNavigate();
  const { showNotification } = useNotification();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    try {
      const res = await api.post("/auth/register", form);
      login({ email: form.email, token: res.data.token });
      setTimeout(() => {
        fetchUser();
        navigate("/", { replace: true });
        showNotification("success", "Account created successfully!");
      }, 100);
    } catch (err: any) {
      setError(err.response?.data?.error || "Registration failed.");
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
            <h2 className="h2 text-center mb-4">Create new account</h2>
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleSubmit}>
              <div className="row mb-3">
                <div className="col">
                  <label className="form-label">First Name</label>
                  <input type="text" className="form-control" value={form.firstName} onChange={(e) => setForm({ ...form, firstName: e.target.value })} required />
                </div>
                <div className="col">
                  <label className="form-label">Last Name</label>
                  <input type="text" className="form-control" value={form.lastName} onChange={(e) => setForm({ ...form, lastName: e.target.value })} required />
                </div>
              </div>
              <div className="mb-3">
                <label className="form-label">Email address</label>
                <input type="email" className="form-control" value={form.email} onChange={(e) => setForm({ ...form, email: e.target.value })} required />
              </div>
              <div className="mb-3">
                <label className="form-label">Password</label>
                <input type="password" className="form-control" value={form.password} onChange={(e) => setForm({ ...form, password: e.target.value })} required minLength={6} />
              </div>
              <div className="form-footer">
                <button type="submit" className="btn btn-primary w-100">Create account</button>
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

export default RegisterPage;
