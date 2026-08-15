import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8081/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Normalize error messages coming back from the Spring Boot backend
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    const message =
      error.response?.data?.message ||
      error.message ||
      'Something went wrong while contacting the server.';
    return Promise.reject(new Error(message));
  }
);

export default apiClient;
