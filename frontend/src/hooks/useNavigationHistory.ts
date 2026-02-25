import { useLocation, useNavigate } from "react-router";
import { useEffect, useRef, useState } from "react";

type HistoryEntry = {
  path: string;
  replace?: boolean;
};

export const useNavigationHistory = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const historyStack = useRef<HistoryEntry[]>([]);
  const forwardStack = useRef<HistoryEntry[]>([]);
  const [canGoBack, setCanGoBack] = useState(false);
  const [canGoForward, setCanGoForward] = useState(false);

  useEffect(() => {
    const path = location.pathname + location.search;
    const replace = window.history.state?.usr?.replace === true;

    if (forwardStack.current.length && forwardStack.current[0].path === path) {
      forwardStack.current.shift();
      setCanGoForward(forwardStack.current.length > 0);
      return;
    }

    if (replace && historyStack.current.length > 0) {
      historyStack.current[historyStack.current.length - 1] = { path, replace };
    } else if (
      historyStack.current.length === 0 ||
      historyStack.current[historyStack.current.length - 1].path !== path
    ) {
      historyStack.current.push({ path });
    }

    setCanGoBack(historyStack.current.length > 1);
  }, [location]);

  const goBack = () => {
    if (!canGoBack) return;
    const current = historyStack.current.pop()!;
    forwardStack.current.unshift(current);

    const prev = historyStack.current[historyStack.current.length - 1];
    setCanGoForward(true);
    setCanGoBack(historyStack.current.length > 1);
    navigate(prev.path, { state: { fromStack: true } });
  };

  const goForward = () => {
    if (!canGoForward) return;
    const next = forwardStack.current.shift()!;
    historyStack.current.push(next);
    setCanGoForward(forwardStack.current.length > 0);
    setCanGoBack(historyStack.current.length > 1);
    navigate(next.path, { state: { fromStack: true } });
  };

  return {
    goBack,
    goForward,
    canGoBack,
    canGoForward,
  };
};
