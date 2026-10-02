# Git Workflow

## Branching Strategy
- `main`: The production-ready code.
- `develop`: The active development branch.
- Feature branches: Created from `develop`, named `feature/JAVA-XX-short-description`.

## Commit Messages
Follow the conventional commits format:
`[Task-ID] <type>: <description>`
Example: `[JAVA-01] feat: add Student entity and repository`

Types:
- `feat`: A new feature
- `fix`: A bug fix
- `docs`: Documentation only changes
- `test`: Adding missing tests or correcting existing tests
- `refactor`: A code change that neither fixes a bug nor adds a feature

## Pull Requests
- PRs must target the `develop` branch.
- PR title should match the feature commit message format.
- A code review is required before merging. Ensure all CI checks (tests, linting) pass.
