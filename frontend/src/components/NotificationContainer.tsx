import { useEffect, useState } from "react";

interface Notification {
  id: number;
  type: "success" | "error" | "warning" | "info";
  message: string;
}

const iconClassMap: Record<Notification["type"], string> = {
  error: "ti-alert-circle",
  warning: "ti-alert-triangle",
  success: "ti-check",
  info: "ti-info-circle",
};

const NotificationContainer = ({ notifications }: { notifications: Notification[] }) => {
  const [exiting, setExiting] = useState<number[]>([]);
  const isLoggedIn = !!localStorage.getItem("token");

  useEffect(() => {
    const handleAutoClose = (e: Event) => {
      const id = (e as CustomEvent).detail as number;

      setExiting((prev) => [...prev, id]);

      // Remove after animation
      setTimeout(() => {
        const removeEvent = new CustomEvent("notification-remove", { detail: id });
        window.dispatchEvent(removeEvent);
      }, 400);
    };

    window.addEventListener("notification-exit", handleAutoClose);
    return () => window.removeEventListener("notification-exit", handleAutoClose);
  }, []);

  const handleManualClose = (id: number) => {
    setExiting((prev) => [...prev, id]);
    setTimeout(() => {
      const closeEvent = new CustomEvent("notification-remove", { detail: id });
      window.dispatchEvent(closeEvent);
    }, 400);
  };

  return (
    <div
      className="position-fixed start-50 translate-middle-x d-flex flex-column align-items-center gap-1"
      style={{ zIndex: 1060, top: isLoggedIn ? "120px" : "80px" }}
    >
      {notifications.map(({ id, type, message }) => {
        const typeClass = type === "error" ? "danger" : type;
        const animationClass = exiting.includes(id) ? "notification-exit" : "notification-enter";

        return (
          <div
            key={id}
            className={`alert alert-${typeClass} alert-dismissible d-flex align-items-center ${animationClass}`}
            role="alert"
          >
            <div className="alert-icon">
              <i className={`ti ${iconClassMap[type]} fs-1 me-2`}></i>
            </div>
            <div className="flex-grow-1">{message}</div>
            <button
              type="button"
              className="btn-close"
              onClick={() => handleManualClose(id)}
              aria-label="Close"
            ></button>
          </div>
        );
      })}
    </div>
  );
};

export default NotificationContainer;
