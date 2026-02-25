import "@tabler/core/dist/css/tabler.min.css";
import "@tabler/icons-webfont/dist/tabler-icons.min.css";
import "@tabler/core/dist/js/tabler.min.js";
import "typeface-inter";

import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { AuthProvider } from "./contexts/auth.tsx";
import { UserProvider } from "./contexts/user.tsx";
import { AuthInterceptor } from "./components/AuthInterceptor.tsx";
import { NotificationProvider } from "./contexts/notification";
import { BrowserRouter } from "react-router";
import "./assets/notifications.css";
import { ThemeProvider } from "./contexts/theme.tsx";

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <BrowserRouter>
      <ThemeProvider>
        <NotificationProvider>
          <AuthProvider>
            <AuthInterceptor />
            <UserProvider>
              <App />
            </UserProvider>
          </AuthProvider>
        </NotificationProvider>
      </ThemeProvider>
    </BrowserRouter>
  </React.StrictMode>,
);
