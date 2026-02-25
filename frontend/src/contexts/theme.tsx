import React, { createContext, useContext, useEffect, useState, ReactNode } from "react";

export type Theme = "light" | "dark";
export type ThemeMode = Theme | "system";

interface ThemeContextType {
  currentTheme: Theme;
  themeMode: ThemeMode;
  setTheme: (mode: ThemeMode) => void;
  toggleTheme: () => void;
  isSystemPreferred: boolean;
}

const ThemeContext = createContext<ThemeContextType | undefined>(undefined);

export const ThemeProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const getSystemTheme = (): Theme => {
    return window.matchMedia && window.matchMedia("(prefers-color-scheme: dark)").matches
      ? "dark"
      : "light";
  };

  const [themeMode, setThemeMode] = useState<ThemeMode>(() => {
    const storedMode = localStorage.getItem("themeMode") as ThemeMode;
    return storedMode || "system";
  });

  const [currentTheme, setCurrentTheme] = useState<Theme>(() => {
    const storedMode = localStorage.getItem("themeMode") as ThemeMode;

    if (storedMode === "light" || storedMode === "dark") {
      return storedMode;
    }

    return getSystemTheme();
  });

  useEffect(() => {
    const mediaQuery = window.matchMedia("(prefers-color-scheme: dark)");

    const handleChange = () => {
      if (themeMode === "system") {
        setCurrentTheme(getSystemTheme());
      }
    };

    mediaQuery.addEventListener("change", handleChange);
    return () => mediaQuery.removeEventListener("change", handleChange);
  }, [themeMode]);

  useEffect(() => {
    if (themeMode === "system") {
      setCurrentTheme(getSystemTheme());
    } else {
      setCurrentTheme(themeMode);
    }

    localStorage.setItem("themeMode", themeMode);
  }, [themeMode]);

  useEffect(() => {
    document.documentElement.setAttribute("data-bs-theme", currentTheme);
  }, [currentTheme]);

  const setTheme = (newMode: ThemeMode) => {
    setThemeMode(newMode);
  };

  const toggleTheme = () => {
    if (themeMode === "system") {
      setThemeMode(currentTheme === "dark" ? "light" : "dark");
    } else {
      setThemeMode(themeMode === "dark" ? "light" : "dark");
    }
  };

  return (
    <ThemeContext.Provider
      value={{
        currentTheme,
        themeMode,
        setTheme,
        toggleTheme,
        isSystemPreferred: themeMode === "system",
      }}
    >
      {children}
    </ThemeContext.Provider>
  );
};

export const useTheme = () => {
  const context = useContext(ThemeContext);
  if (context === undefined) {
    throw new Error("useTheme must be used within a ThemeProvider");
  }
  return context;
};
