import { AxiosError, AxiosInstance, AxiosResponse } from "axios";

type Options = {
  onUnauthorized: () => void;
};

let isInterceptorRegistered = false;

export const setupAuthInterceptor = (
  api: AxiosInstance,
  { onUnauthorized }: Options,
): void => {
  if (isInterceptorRegistered) return;

  api.interceptors.response.use(
    (response: AxiosResponse) => response,
    (error: AxiosError) => {
      if (error.response?.status === 401) {
        onUnauthorized();
      }
      return Promise.reject(error);
    },
  );

  isInterceptorRegistered = true;
};
