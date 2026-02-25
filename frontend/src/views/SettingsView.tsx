import { useState } from "react";
import { api } from "../services/api";
import { useUser } from "../contexts/user";
import { useAuth } from "../contexts/auth";
import { useTheme } from "../contexts/theme";
import { useNotification } from "../contexts/notification";

const SettingsView = () => {
  const { user, fetchUser, userId } = useUser();
  const { login } = useAuth();
  const { themeMode, setTheme } = useTheme();
  const { showNotification } = useNotification();

  const [firstName, setFirstName] = useState(user?.firstName || "");
  const [lastName, setLastName] = useState(user?.lastName || "");
  const [bio, setBio] = useState(user?.bio || "");
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [saving, setSaving] = useState(false);

  const handleProfile = async (e: React.FormEvent) => {
    e.preventDefault();
    setSaving(true);
    try {
      await api.put("/users/me", { firstName, lastName, bio });
      fetchUser();
      showNotification("success", "Profile updated!");
    } catch {
      showNotification("error", "Failed to update profile.");
    } finally {
      setSaving(false);
    }
  };

  const handlePassword = async (e: React.FormEvent) => {
    e.preventDefault();
    setSaving(true);
    try {
      await api.put(`/users/${userId}/password`, { currentPassword, newPassword });
      setCurrentPassword("");
      setNewPassword("");
      showNotification("success", "Password changed!");
    } catch (err: any) {
      showNotification("error", err.response?.data?.error || "Failed to change password.");
    } finally {
      setSaving(false);
    }
  };

  const handleAvatarUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;
    const formData = new FormData();
    formData.append("avatar", file);
    try {
      await api.post(`/users/${userId}/avatar`, formData, {
        headers: { "Content-Type": "multipart/form-data" },
      });
      fetchUser();
      showNotification("success", "Avatar updated!");
    } catch {
      showNotification("error", "Failed to upload avatar.");
    }
  };

  return (
    <div className="container-xl py-4">
      <div className="page-header mb-4">
        <h2 className="page-title"><i className="ti ti-settings me-2" />Settings</h2>
      </div>

      <div className="row g-4">
        {/* Profile */}
        <div className="col-md-6">
          <div className="card">
            <div className="card-header"><h3 className="card-title">Profile</h3></div>
            <div className="card-body">
              <div className="mb-3 text-center">
                <span
                  className="avatar avatar-xl mb-2"
                  style={user?.avatarUrl ? { backgroundImage: `url(${user.avatarUrl})` } : {}}
                >
                  {!user?.avatarUrl && (user?.firstName?.[0] || "U")}
                </span>
                <div>
                  <label className="btn btn-outline-primary btn-sm">
                    <i className="ti ti-upload me-1" /> Upload Avatar
                    <input type="file" accept="image/*" className="d-none" onChange={handleAvatarUpload} />
                  </label>
                </div>
              </div>
              <form onSubmit={handleProfile}>
                <div className="row mb-3">
                  <div className="col">
                    <label className="form-label">First name</label>
                    <input type="text" className="form-control" value={firstName} onChange={(e) => setFirstName(e.target.value)} />
                  </div>
                  <div className="col">
                    <label className="form-label">Last name</label>
                    <input type="text" className="form-control" value={lastName} onChange={(e) => setLastName(e.target.value)} />
                  </div>
                </div>
                <div className="mb-3">
                  <label className="form-label">Bio</label>
                  <textarea className="form-control" rows={3} value={bio} onChange={(e) => setBio(e.target.value)} />
                </div>
                <button type="submit" className="btn btn-primary" disabled={saving}>
                  <i className="ti ti-device-floppy me-1" /> Save Profile
                </button>
              </form>
            </div>
          </div>
        </div>

        {/* Password + Theme */}
        <div className="col-md-6">
          <div className="card mb-4">
            <div className="card-header"><h3 className="card-title">Change Password</h3></div>
            <div className="card-body">
              <form onSubmit={handlePassword}>
                <div className="mb-3">
                  <label className="form-label">Current password</label>
                  <input type="password" className="form-control" value={currentPassword} onChange={(e) => setCurrentPassword(e.target.value)} required />
                </div>
                <div className="mb-3">
                  <label className="form-label">New password</label>
                  <input type="password" className="form-control" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} required minLength={6} />
                </div>
                <button type="submit" className="btn btn-primary" disabled={saving}>
                  <i className="ti ti-lock me-1" /> Change Password
                </button>
              </form>
            </div>
          </div>

          <div className="card">
            <div className="card-header"><h3 className="card-title">Appearance</h3></div>
            <div className="card-body">
              <div className="mb-3">
                <label className="form-label">Theme</label>
                <div className="d-flex gap-2">
                  {(["light", "dark", "system"] as const).map((mode) => (
                    <button
                      key={mode}
                      className={`btn ${themeMode === mode ? "btn-primary" : "btn-outline-secondary"}`}
                      onClick={() => setTheme(mode)}
                    >
                      <i className={`ti ti-${mode === "light" ? "sun" : mode === "dark" ? "moon" : "device-desktop"} me-1`} />
                      {mode.charAt(0).toUpperCase() + mode.slice(1)}
                    </button>
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SettingsView;
