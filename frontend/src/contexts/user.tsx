import React, { createContext, useState, useEffect, useContext, ReactNode } from "react";
import { api } from "../services/api";
import { AxiosResponse } from "axios";

export enum RoleName {
  ADMIN = "ADMIN",
  CRITIC = "CRITIC",
  USER = "USER",
}

interface User {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  roleName: string;
  avatarUrl: string;
  bio?: string;
  createdAt?: string;
  reviewCount?: number;
}

interface UserContextType {
  user: User | null;
  loading: boolean;
  error: string | null;
  fetchUser: () => void;
  userId: number;
  userFirstName: string;
  userLastName: string;
  userEmail: string;
  userRoleName: string;
  userAvatarUrl: string;
}

const UserContext = createContext<UserContextType | undefined>(undefined);

export const useUser = () => {
  const context = useContext(UserContext);
  if (!context) {
    throw new Error("useUser must be used within a UserProvider");
  }
  return context;
};

export const UserProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchUser = async () => {
    try {
      setLoading(true);
      const response: AxiosResponse<User> = await api.get("/users/me");
      setUser(response.data);
      setError(null);
    } catch (error) {
      setError("Failed to fetch user data.");
      setUser(null);
    } finally {
      setLoading(false);
    }
  };

  const userId = user?.id ?? 0;
  const userFirstName = user?.firstName ?? "";
  const userLastName = user?.lastName ?? "";
  const userEmail = user?.email ?? "";
  const userRoleName = user?.roleName ?? "USER";
  const userAvatarUrl = user?.avatarUrl ?? "";

  useEffect(() => {
    const token = localStorage.getItem("token");
    const timeout = setTimeout(() => {
      if (token) {
        fetchUser();
      } else {
        setLoading(false);
      }
    }, 300);
    return () => clearTimeout(timeout);
  }, []);

  return (
    <UserContext.Provider
      value={{ user, loading, error, fetchUser, userId, userFirstName, userLastName, userEmail, userRoleName, userAvatarUrl }}
    >
      {children}
    </UserContext.Provider>
  );
};
