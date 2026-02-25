import { useUser } from "../../contexts/user";
import { useAuth } from "../../contexts/auth";
import { useNavigate, Link, useLocation } from "react-router";
import { getNavItemClass } from "../../utils/navigationUtils";
import UserDropdown from "./UserDropdown";
import { useNotification } from "../../contexts/notification";

const HeaderNav = () => {
  const { userId, userRoleName } = useUser();
  const { logout, state } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const { showNotification } = useNotification();

  const handleLogout = () => {
    logout();
    setTimeout(() => {
      navigate("/login", { replace: true });
      showNotification("info", "Successfully logged out.");
    }, 50);
  };

  return (
    <header className="navbar navbar-expand-lg">
      <div className="container-xl">
        <Link to="/" className="navbar-brand navbar-brand-autodark d-none-navbar-horizontal pe-0 pe-md-3">
          <img src="/movievault.svg" width="32" height="32" alt="MovieVault" className="navbar-brand-image me-2" />
          <span className="fw-bold">MovieVault</span>
        </Link>

        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbar-menu">
          <span className="navbar-toggler-icon" />
        </button>

        <div className="collapse navbar-collapse" id="navbar-menu">
          <ul className="navbar-nav me-auto">
            <li className={getNavItemClass("/", location.pathname)}>
              <Link className="nav-link" to="/"><i className="ti ti-home fs-2 me-1" /> Home</Link>
            </li>
            <li className={getNavItemClass("/movies", location.pathname)}>
              <Link className="nav-link" to="/movies"><i className="ti ti-movie fs-2 me-1" /> Movies</Link>
            </li>
            {state.loggedIn && (
              <>
                <li className={getNavItemClass("/my-reviews", location.pathname)}>
                  <Link className="nav-link" to="/my-reviews"><i className="ti ti-star fs-2 me-1" /> My Reviews</Link>
                </li>
                <li className={getNavItemClass("/watchlist", location.pathname)}>
                  <Link className="nav-link" to="/watchlist"><i className="ti ti-bookmark fs-2 me-1" /> Watchlist</Link>
                </li>
              </>
            )}
            {userRoleName === "ADMIN" && (
              <li className={getNavItemClass("/admin", location.pathname)}>
                <Link className="nav-link" to="/admin"><i className="ti ti-dashboard fs-2 me-1" /> Admin</Link>
              </li>
            )}
          </ul>

          {state.loggedIn ? (
            <UserDropdown handleLogout={handleLogout} />
          ) : (
            <div className="nav-item d-flex gap-2">
              <Link to="/login" className="btn btn-outline-primary btn-sm">Login</Link>
              <Link to="/register" className="btn btn-primary btn-sm">Register</Link>
            </div>
          )}
        </div>
      </div>
    </header>
  );
};

export default HeaderNav;
