import React from "react";
import { Routes, Route, Navigate } from "react-router";
import { useAuth } from "./contexts/auth";
import { useUser } from "./contexts/user";
import DashboardLayout from "./layouts/DashboardLayout";
import HomeView from "./views/HomeView";
import MoviesView from "./views/MoviesView";
import MovieDetailView from "./views/MovieDetailView";
import LoginView from "./views/LoginView";
import RegisterView from "./views/RegisterView";
import WatchlistView from "./views/WatchlistView";
import MyReviewsView from "./views/MyReviewsView";
import SettingsView from "./views/SettingsView";
import NotFoundView from "./views/NotFoundView";
import AdminDashboard from "./views/Admin/AdminDashboard";
import UserManagementPage from "./views/Admin/UserManagementPage";

const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
  const { state } = useAuth();
  if (!state.loggedIn) return <Navigate to="/login" replace />;
  return <>{children}</>;
};

const AdminRoute = ({ children }: { children: React.ReactNode }) => {
  const { state } = useAuth();
  const { userRoleName } = useUser();
  if (!state.loggedIn) return <Navigate to="/login" replace />;
  if (userRoleName !== "ADMIN") return <Navigate to="/" replace />;
  return <>{children}</>;
};

const App = () => {
  const { state } = useAuth();
  const { loading } = useUser();

  if (state.loading || (state.loggedIn && loading)) {
    return (
      <div className="page page-center d-flex align-items-center justify-content-center vh-100">
        <div className="container container-slim py-4">
          <div className="text-center">
            <div className="mb-3">
              <i className="ti ti-movie text-primary" style={{ fontSize: 48 }} />
            </div>
            <div className="text-secondary mb-3">Loading MovieVault...</div>
            <div className="progress progress-sm">
              <div className="progress-bar progress-bar-indeterminate" />
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <Routes>
      <Route element={<DashboardLayout />}>
        <Route path="/" element={<HomeView />} />
        <Route path="/movies" element={<MoviesView />} />
        <Route path="/movies/:id" element={<MovieDetailView />} />
        <Route path="/watchlist" element={<ProtectedRoute><WatchlistView /></ProtectedRoute>} />
        <Route path="/my-reviews" element={<ProtectedRoute><MyReviewsView /></ProtectedRoute>} />
        <Route path="/settings" element={<ProtectedRoute><SettingsView /></ProtectedRoute>} />
        <Route path="/admin" element={<AdminRoute><AdminDashboard /></AdminRoute>} />
        <Route path="/admin/users" element={<AdminRoute><UserManagementPage /></AdminRoute>} />
      </Route>

      <Route path="/login" element={state.loggedIn ? <Navigate to="/" replace /> : <LoginView />} />
      <Route path="/register" element={state.loggedIn ? <Navigate to="/" replace /> : <RegisterView />} />

      <Route path="*" element={<NotFoundView />} />
    </Routes>
  );
};

export default App;
