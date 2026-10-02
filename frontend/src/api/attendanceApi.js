import apiClient from './apiClient.js'

export const attendanceApi = {
  markAttendance: (data) => apiClient.post('/attendance', data).then(res => res.data),
  getByStudent: (studentId) => apiClient.get(`/attendance/student/${studentId}`).then(res => res.data),
  getBySubject: (subjectId) => apiClient.get(`/attendance/subject/${subjectId}`).then(res => res.data),
  getSummary: (studentId, subjectId) =>
    apiClient.get(`/attendance/summary/${studentId}/${subjectId}`).then(res => res.data),
  update: (id, status) => apiClient.put(`/attendance/${id}`, null, { params: { status } }).then(res => res.data),
  delete: (id) => apiClient.delete(`/attendance/${id}`),
}
