import { useUser } from "../../contexts/user";
import { Link } from "react-router";

const UserDropdown = ({ handleLogout }: { handleLogout: () => void }) => {
  const { userFirstName, userLastName, userAvatarUrl, userRoleName } = useUser();

  return (
    <div className="nav-item dropdown">
      <a href="#" className="nav-link d-flex lh-1 text-reset p-0" data-bs-toggle="dropdown">
        <span
          className="avatar avatar-sm rounded-circle"
          style={userAvatarUrl ? { backgroundImage: `url(${userAvatarUrl})` } : {}}
        >
          {!userAvatarUrl && (userFirstName?.[0] ?? "U")}
        </span>
        <div className="d-none d-xl-block ps-2">
          <div>{userFirstName} {userLastName}</div>
          <div className="mt-1 small text-secondary">{userRoleName}</div>
        </div>
      </a>
      <div className="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
        <Link to="/settings" className="dropdown-item">
          <i className="ti ti-settings me-2" /> Settings
        </Link>
        <div className="dropdown-divider" />
        <a href="#" className="dropdown-item" onClick={(e) => { e.preventDefault(); handleLogout(); }}>
          <i className="ti ti-logout me-2" /> Logout
        </a>
      </div>
    </div>
  );
};

export default UserDropdown;
