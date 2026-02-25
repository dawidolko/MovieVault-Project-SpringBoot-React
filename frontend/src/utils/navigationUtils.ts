export const getNavItemClass = (path: string, currentPath: string) => {
  if (path === "/") {
    return currentPath === path ? "nav-item active" : "nav-item";
  } else {
    return currentPath.includes(path) ? "nav-item active" : "nav-item";
  }
};
