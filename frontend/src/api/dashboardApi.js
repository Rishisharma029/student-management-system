import apiClient from './apiClient.js'

export const dashboardApi = {
  getStats: () => apiClient.get('/dashboard/stats').then(res => res.data),
}
