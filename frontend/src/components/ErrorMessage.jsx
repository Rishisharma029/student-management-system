/**
 * ErrorMessage — Displays a user-friendly error alert.
 * Props:
 *   message (string) — the error text to display
 */
function ErrorMessage({ message }) {
  if (!message) return null
  return (
    <div className="alert alert-error" role="alert">
      {message}
    </div>
  )
}

export default ErrorMessage
