import { useEffect } from "react";
import { useAuth } from "../contexts/auth";
import { setupAuthInterceptor } from "../services/setupAxiosAuth";
import { createAxiosInstance } from "../services/api";
import { useNavigate } from "react-router";

export const AuthInterceptor = (): null => {
  const { state, logout } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const instance = createAxiosInstance();
    setupAuthInterceptor(instance, {
      onUnauthorized: () => {
        logout();
        navigate("/login", { replace: true });
      },
    });
  }, [state.user]);

  return null;
};
