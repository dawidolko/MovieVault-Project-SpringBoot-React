import { RoleName } from "../contexts/user";

export const getRoleDisplayName = (role: RoleName): string => {
  const roleMap: Record<RoleName, string> = {
    [RoleName.ADMIN]: "Admin",
    [RoleName.CRITIC]: "Critic",
    [RoleName.USER]: "User",
  };

  return roleMap[role] ?? "Unknown role";
};
