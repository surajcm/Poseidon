# Code Conventions

## Java
- Use `CamelCase` for class names, `camelCase` for variables and methods.
- Annotate Spring components (`@Service`, `@Repository`, `@Controller`).
- Prefer constructor injection.
- Use meaningful names, avoid abbreviations.
- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html).

## Gradle
- Use `build.gradle` for main build logic.
- Keep dependencies in `gradle/dependencies.gradle`.
- Use tasks and plugins as per Gradle best practices.

## SQL
- Uppercase for SQL keywords.
- Table/column names in `snake_case`.
- Indent queries for readability.

## General
- Write Javadoc for public classes/methods.
- Add comments for complex logic.
- Use Checkstyle and PMD for static analysis.

