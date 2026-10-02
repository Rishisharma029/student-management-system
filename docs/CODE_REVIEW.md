# Code Review Guidelines

## 1. Architecture and Structure
- [ ] Are the controllers, services, and repositories cleanly separated?
- [ ] Is business logic kept out of the controllers?
- [ ] Are DTOs used instead of exposing entities directly to the client?

## 2. Best Practices
- [ ] Are variables and methods named descriptively following Java conventions?
- [ ] Are hardcoded values avoided?
- [ ] Is dependency injection implemented via constructors rather than field injection (`@Autowired`)?

## 3. Error Handling
- [ ] Are exceptions caught and handled properly?
- [ ] Does the API return appropriate HTTP status codes (200, 201, 400, 404, 500)?
- [ ] Are error messages informative but safe (no stack traces exposed)?

## 4. Database & Performance
- [ ] Are database transactions used where necessary (`@Transactional`)?
- [ ] Is the N+1 query problem avoided in relationships?

## 5. Testing
- [ ] Are unit tests provided for all core logic?
- [ ] Do tests cover both happy path and edge cases?
- [ ] Are external dependencies mocked appropriately in unit tests?
