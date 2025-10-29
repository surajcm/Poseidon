# Gradle Guidelines

## Build
- Use `build.gradle` for main build logic
- Use `gradle/dependencies.gradle` for dependency management
- Use `gradlew` for consistent builds

## Tasks
- Standard tasks: `build`, `test`, `clean`
- Custom tasks: define in `build.gradle` or separate scripts

## Dependency Management
- Prefer stable releases
- Use `implementation` for main dependencies, `testImplementation` for test deps

## Static Analysis
- Integrate Checkstyle and PMD via Gradle plugins

