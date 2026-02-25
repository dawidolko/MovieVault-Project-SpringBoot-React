import { useState } from "react";
import { useUser } from "../../contexts/user";
import { api } from "../../services/api";
import { useNotification } from "../../contexts/notification";
import { useTheme } from "../../contexts/theme";

const SettingsPage = () => {
  const { userFirstName, userLastName, userEmail, fetchUser } = useUser();
  const { showNotification } = useNotification();
  const { themeMode, setTheme } = useTheme();
  const [form, setForm] = useState({ firstName: userFirstName, lastName: userLastName, bio: "" });

  const saveProfile = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.put("/users/me", form);
      fetchUser();
      showNotification("success", "Profile updated.");
    } catch { showNotification("error", "Failed to update profile."); }
  };

  return (
    <div className="page-wrapper">
      <div className="page-header d-print-none"><div className="container-xl"><h2 className="page-title">Settings</h2></div></div>
      <div className="page-body">
        <div className="container-xl">
          <div className="row">
            <div className="col-lg-8">
              <div className="card mb-3">
                <div className="card-header"><h3 className="card-title">Profile</h3></div>
                <div className="card-body">
                  <form onSubmit={saveProfile}>
                    <div className="row mb-3">
                      <div className="col"><label className="form-label">First Name</label><input type="text" className="form-control" value={form.firstName} onChange={(e) => setForm({ ...form, firstName: e.target.value })} /></div>
                      <div className="col"><label className="form-label">Last Name</label><input type="text" className="form-control" value={form.lastName} onChange={(e) => setForm({ ...form, lastName: e.target.value })} /></div>
                    </div>
                    <div className="mb-3"><label className="form-label">Bio</label><textarea className="form-control" rows={3} value={form.bio} onChange={(e) => setForm({ ...form, bio: e.target.value })} /></div>
                    <button type="submit" className="btn btn-primary">Save Changes</button>
                  </form>
                </div>
              </div>
            </div>
            <div className="col-lg-4">
              <div className="card">
                <div className="card-header"><h3 className="card-title">Appearance</h3></div>
                <div className="card-body">
                  <div className="mb-3">
                    <label className="form-label">Theme</label>
                    <select className="form-select" value={themeMode} onChange={(e) => setTheme(e.target.value as any)}>
                      <option value="system">System</option>
                      <option value="light">Light</option>
                      <option value="dark">Dark</option>
                    </select>
                  </div>
                  <div className="text-secondary small">Email: {userEmail}</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SettingsPage;
