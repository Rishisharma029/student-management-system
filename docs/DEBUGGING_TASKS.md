# Debugging Exercises

- **BUG-01**: "NullPointerException when saving a student without email."
  - **Description**: The application crashes when a POST request is sent missing the email field.
  - **Hint**: Ensure that the incoming DTO is validated before reaching the service layer. Check your `@Valid` annotation and exception handler.

- **BUG-02**: "LazyInitializationException when fetching student courses."
  - **Description**: Accessing `student.getCourses()` outside an active transaction throws an error.
  - **Hint**: Consider adding `@Transactional` to the service method, or fetching the courses eagerly with a `JOIN FETCH` query.

- **BUG-03**: "HTTP 500 instead of 400 when validation fails."
  - **Description**: The user sees a generic server error instead of a bad request message when they provide an invalid email.
  - **Hint**: The `MethodArgumentNotValidException` needs to be caught in your `@ControllerAdvice` and mapped to a 400 response.

- **BUG-04**: "Pagination not returning the correct page number."
  - **Description**: Requesting page 1 returns the second page of results.
  - **Hint**: Spring Data JPA's `PageRequest` is 0-indexed! Page 0 is the first page.

- **BUG-05**: "DELETE student throws foreign key constraint violation."
  - **Description**: Attempting to delete a student who has attendance records fails.
  - **Hint**: Check the `cascade` property on the `@OneToMany` attendance relationship inside the `Student` entity. You might need `CascadeType.REMOVE`.
