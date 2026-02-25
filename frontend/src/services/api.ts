import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from "axios";

let axiosInstance: AxiosInstance | null = null;

function createAxiosInstance(): AxiosInstance {
  if (axiosInstance) return axiosInstance;

  axiosInstance = axios.create({
    baseURL: "/api",
  });

  axiosInstance.interceptors.request.use((config) => {
    const isPublicRoute = config.url?.includes("/auth/");
    if (!isPublicRoute) {
      const token = localStorage.getItem("token");
      if (token) {
        config.headers = {
          ...config.headers,
          Authorization: `Bearer ${token}`,
        } as any;
      }
    }
    return config;
  });

  return axiosInstance;
}

export function resetAxiosInstance() {
  axiosInstance = null;
}

export const api = {
  get: (url: string, params?: any, config?: AxiosRequestConfig): Promise<AxiosResponse> => {
    const instance = createAxiosInstance();
    return instance.get(url, { ...config, params });
  },
  post: (url: string, data?: any, config?: AxiosRequestConfig): Promise<AxiosResponse> => {
    const instance = createAxiosInstance();
    return instance.post(url, data, config);
  },
  put: (url: string, data?: any, config?: AxiosRequestConfig): Promise<AxiosResponse> => {
    const instance = createAxiosInstance();
    return instance.put(url, data, config);
  },
  delete: (url: string, config?: AxiosRequestConfig): Promise<AxiosResponse> => {
    const instance = createAxiosInstance();
    return instance.delete(url, config);
  },
};

export { createAxiosInstance, axiosInstance };
