import apiClient from './apiClient.js'

/**
 * ============================================================
 *  Student API — All API calls related to students
 * ============================================================
 * 
 * Each function corresponds to one backend endpoint.
 * We return response.data because that's the actual payload
 * (not the Axios wrapper object).
 * ============================================================
 */
export const studentApi = {
  /**
   * GET /api/students
   * Fetch all students, with optional search/filter
   */
  getAll: (params = {}) =>
    apiClient.get('/students', { params }).then(res => res.data),

  /**
   * GET /api/students/:id
   * Fetch a single student by ID
   */
  getById: (id) =>
    apiClient.get(`/students/${id}`).then(res => res.data),

  /**
   * POST /api/students
   * Create a new student
   */
  create: (studentData) =>
    apiClient.post('/students', studentData).then(res => res.data),

  /**
   * PUT /api/students/:id
   * Update an existing student
   */
  update: (id, studentData) =>
    apiClient.put(`/students/${id}`, studentData).then(res => res.data),

  /**
   * DELETE /api/students/:id
   * Delete a student
   */
  delete: (id) =>
    apiClient.delete(`/students/${id}`),
}
