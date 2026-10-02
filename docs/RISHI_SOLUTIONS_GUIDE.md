# Rishi's Complete Solution Guide

Welcome to the `rishi-solutions` branch.

## 1. Purpose of this branch

`main` is where **you learn by implementing**.
`rishi-solutions` is where **you learn by example**.

This branch contains a complete, working implementation of the Student Management System. It serves as an **educational reference**, showing exactly how to write readable, robust, and standard Java code in a Spring Boot application.

## 2. How you should use it

The recommended workflow is:
1. **Attempt the task** on your own in the `main` branch.
2. **Run the tests** locally to see what fails.
3. **Debug** the errors.
4. If you get stuck, look at `docs/HINTS.md`.
5. **Only then**, inspect the solution in `docs/solutions/` or check out this branch.
6. Compare your approach with the reference implementation.
7. Understand **WHY** a specific decision was made.

## 3. A learning reference, not a copy-paste cheat sheet

The implementations here prioritize **readability** over cleverness. You might have found a shorter way to write something using advanced Streams, but this code explicitly tries to be accessible and robust. Do not blindly copy-paste this code. Understand the flow.

## 4. Comparing branches

To compare your current progress on `main` against this solution branch, use Git:

```bash
# See a summary of what changed
git diff main...rishi-solutions --stat

# See the actual code diff for a specific file
git diff main...rishi-solutions -- backend/src/main/java/com/example/studentmanagement/service/StudentService.java
```

For every task, consult the [SOLUTION_INDEX.md](./SOLUTION_INDEX.md) to find the detailed explanation of how it was built.
