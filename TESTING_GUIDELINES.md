# Testing Guidelines

## Test Types
- Unit tests for business logic
- Integration tests for service/repository layers
- Use mocks for external dependencies

## Frameworks
- JUnit 5 for unit/integration tests
- Mockito for mocking

## Conventions
- Place tests in `src/test/java`
- Name test classes as `ClassNameTest`
- Aim for high coverage, but focus on meaningful tests

## Running Tests
- Use `./gradlew test` to run all tests

