import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import App from './App.jsx'

/**
 * Entry point for the React application.
 * 
 * BrowserRouter wraps the entire app so react-router-dom
 * can intercept URL changes and render different pages.
 */
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>
)
