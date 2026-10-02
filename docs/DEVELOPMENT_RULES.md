# 📜 Development Rules

These are the rules of this codebase. Read them before writing any code.

---

## Architecture Rules

### ❌ Never bypass the service layer
```java
// WRONG — never call repository directly from controller
@GetMapping("/students/{id}")
public Student get(@PathVariable Long id) {
    return studentRepository.findById(id).get(); // NO!
}

// CORRECT — always go through the service
@GetMapping("/students/{id}")
public ResponseEntity<StudentResponseDTO> get(@PathVariable Long id) {
    return ResponseEntity.ok(studentService.getStudentById(id)); // YES!
}
```

### ❌ Never put business logic in controllers
```java
// WRONG — validation logic in controller
@PostMapping
public ResponseEntity<?> create(@RequestBody StudentRequestDTO dto) {
    if (studentRepo.existsByEmail(dto.getEmail())) { // NO!
        return ResponseEntity.status(409).build();
    }
    ...
}

// CORRECT — business logic in service
// The controller just calls the service and returns the result
```

### ❌ Never expose JPA entities directly from the API
```java
// WRONG — returning raw entity
public Student getStudent(Long id) { ... }

// CORRECT — always map to DTO before returning
public StudentResponseDTO getStudent(Long id) { ... }
```

---

## Code Style Rules

### Use meaningful variable names
```java
// WRONG
Student s = repo.findById(id).orElseThrow();

// CORRECT
Student existingStudent = studentRepository.findById(id)
    .orElseThrow(() -> new StudentNotFoundException(id));
```

### Always throw custom exceptions — never return null
```java
// WRONG — returning null
public StudentResponseDTO getStudentById(Long id) {
    Optional<Student> student = studentRepository.findById(id);
    return student.isPresent() ? mapToDTO(student.get()) : null; // NO!
}

// CORRECT — throw an exception
public StudentResponseDTO getStudentById(Long id) {
    return studentRepository.findById(id)
        .map(this::mapEntityToResponseDTO)
        .orElseThrow(() -> new StudentNotFoundException(id)); // YES!
}
```

---

## Security Rules

### Never commit credentials
```properties
# NEVER commit this to Git:
spring.datasource.password=MyRealPassword123

# CORRECT — use environment variables or local overrides
spring.datasource.password=${DB_PASSWORD}
```

### Files that must stay out of Git
```
application-local.properties   ← contains your real DB password
.env                           ← if you use environment files
```

---

## Git Rules

### Write meaningful commit messages
```bash
# WRONG
git commit -m "fix"
git commit -m "stuff"

# CORRECT
git commit -m "feat: implement student search by name and email"
git commit -m "fix: throw StudentNotFoundException instead of returning null"
git commit -m "test: add unit tests for attendance percentage calculation"
```

### Commit format
```
[type]: [short description]

Types:
  feat    = new feature
  fix     = bug fix
  test    = adding tests
  docs    = documentation
  refactor = code cleanup (no behavior change)
```

### Never commit
```
target/          ← Maven build output
node_modules/    ← npm packages
.idea/           ← IDE configuration
*.class          ← compiled Java files
```

---

## Testing Rules

### Test before you push
```bash
mvn test
```

### Each test should test ONE thing
```java
// WRONG — one test testing too many things
@Test
void allStudentOperations() {
    // create, then update, then delete — too much in one test
}

// CORRECT — separate tests for each operation
@Test
void createStudent_Success() { ... }

@Test
void createStudent_ThrowsException_WhenEmailDuplicate() { ... }
```
