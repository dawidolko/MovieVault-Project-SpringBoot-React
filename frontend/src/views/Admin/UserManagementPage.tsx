import { useEffect, useState } from "react";
import { api } from "../../services/api";
import { useNotification } from "../../contexts/notification";

const UserManagementPage = () => {
  const [users, setUsers] = useState<any>({ content: [] });
  const { showNotification } = useNotification();

  const load = (page = 0) => { api.get("/admin/users", { page, size: 20 }).then((r) => setUsers(r.data)).catch(() => {}); };
  useEffect(() => { load(); }, []);

  const changeRole = async (id: number, roleName: string) => {
    try {
      await api.put(`/admin/users/${id}/role`, { roleName });
      showNotification("success", "Role updated.");
      load();
    } catch { showNotification("error", "Failed to update role."); }
  };

  const deleteUser = async (id: number) => {
    if (!confirm("Are you sure?")) return;
    try {
      await api.delete(`/admin/users/${id}`);
      showNotification("success", "User deleted.");
      load();
    } catch { showNotification("error", "Failed to delete user."); }
  };

  return (
    <div className="page-wrapper">
      <div className="page-header d-print-none"><div className="container-xl"><h2 className="page-title">User Management</h2></div></div>
      <div className="page-body">
        <div className="container-xl">
          <div className="card">
            <div className="table-responsive">
              <table className="table table-vcenter card-table">
                <thead><tr><th>Name</th><th>Email</th><th>Role</th><th>Actions</th></tr></thead>
                <tbody>
                  {users.content?.map((u: any) => (
                    <tr key={u.id}>
                      <td>{u.firstName} {u.lastName}</td>
                      <td>{u.email}</td>
                      <td>
                        <select className="form-select form-select-sm" value={u.roleName} onChange={(e) => changeRole(u.id, e.target.value)} style={{ width: 120 }}>
                          <option value="USER">USER</option>
                          <option value="CRITIC">CRITIC</option>
                          <option value="ADMIN">ADMIN</option>
                        </select>
                      </td>
                      <td><button className="btn btn-sm btn-outline-danger" onClick={() => deleteUser(u.id)}>Delete</button></td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserManagementPage;
