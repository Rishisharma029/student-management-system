import axios from 'axios'

/**
 * ============================================================
 *  API Client — Centralized Axios configuration
 * ============================================================
 * 
 * All API calls go through this client.
 * Benefits:
 * - Single place to configure base URL
 * - Consistent error handling
 * - Easy to add auth headers later (just one interceptor)
 * ============================================================
 */
const apiClient = axios.create({
  baseURL: '/api',          // Vite proxy forwards this to http://localhost:8080/api
  timeout: 10000,           // 10 seconds before giving up
  headers: {
    'Content-Type': 'application/json',
  },
})

/**
 * Response interceptor — runs after every response.
 * If the request fails, we extract a readable error message.
 */
apiClient.interceptors.response.use(
  // Success: just return the response as-is
  (response) => response,

  // Error: extract a clean message
  (error) => {
    if (error.response) {
      // The server responded with an error status (4xx, 5xx)
      const { data } = error.response
      const message = data?.message || data?.error || 'An error occurred'
      return Promise.reject(new Error(message))
    } else if (error.request) {
      // The request was made but no response received
      return Promise.reject(new Error('Unable to connect to the server. Is the backend running?'))
    } else {
      return Promise.reject(new Error('Request configuration error'))
    }
  }
)

export default apiClient
