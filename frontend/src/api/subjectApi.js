import apiClient from './apiClient.js'

export const subjectApi = {
  getAll: () => apiClient.get('/subjects').then(res => res.data),
  getById: (id) => apiClient.get(`/subjects/${id}`).then(res => res.data),
  create: (data) => apiClient.post('/subjects', data).then(res => res.data),
  update: (id, data) => apiClient.put(`/subjects/${id}`, data).then(res => res.data),
  delete: (id) => apiClient.delete(`/subjects/${id}`),
}
