import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

/**
 * Vite configuration
 * 
 * The proxy setting is very important:
 * When the React app calls /api/..., Vite forwards it to
 * http://localhost:8080 (the Spring Boot backend).
 * This avoids CORS issues during development.
 */
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      // Forward /api requests to the Spring Boot server
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})
