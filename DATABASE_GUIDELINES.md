# Database Guidelines

## SQL Conventions
- Use uppercase for SQL keywords
- Table/column names in `snake_case`
- Use primary keys (`id`), foreign keys with clear naming

## Migrations
- Use Flyway or Liquibase for schema migrations
- Place migration scripts in `src/main/resources/db/migration`

## Best Practices
- Normalize tables where possible
- Index columns used in queries
- Document schema changes
