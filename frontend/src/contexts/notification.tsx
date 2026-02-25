import { createContext, useContext, useState, ReactNode, useEffect } from "react";
import NotificationContainer from "../components/NotificationContainer";

type NotificationType = "success" | "error" | "warning" | "info";

interface Notification {
  id: number;
  type: NotificationType;
  message: string;
}

interface NotificationContextType {
  showNotification: (type: NotificationType, message: string, timeout?: number) => void;
}

const NotificationContext = createContext<NotificationContextType | undefined>(undefined);
let nextId = 0;

export const NotificationProvider = ({ children }: { children: ReactNode }) => {
  const [notifications, setNotifications] = useState<Notification[]>([]);

  const showNotification = (type: NotificationType, message: string, timeout = 3000) => {
    const id = nextId++;
    setNotifications((prev) => [...prev, { id, type, message }]);

    if (timeout !== -1) {
      setTimeout(() => {
        const exitEvent = new CustomEvent("notification-exit", { detail: id });
        window.dispatchEvent(exitEvent);
      }, timeout - 400);
    }
  };

  useEffect(() => {
    const listener = (e: Event) => {
      const id = (e as CustomEvent).detail;
      setNotifications((prev) => prev.filter((n) => n.id !== id));
    };

    window.addEventListener("notification-remove", listener);
    return () => window.removeEventListener("notification-remove", listener);
  }, []);

  return (
    <NotificationContext.Provider value={{ showNotification }}>
      {children}
      <NotificationContainer notifications={notifications} />
    </NotificationContext.Provider>
  );
};

export const useNotification = (): NotificationContextType => {
  const context = useContext(NotificationContext);
  if (!context) throw new Error("useNotification must be used within a NotificationProvider");
  return context;
};
