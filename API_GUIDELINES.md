# API Guidelines

## REST Principles
- Use RESTful endpoints: `/api/resource`
- Use standard HTTP methods: GET, POST, PUT, DELETE
- Use plural nouns for resources

## Request/Response
- Accept/return JSON
- Use appropriate status codes (200, 201, 400, 404, 500)
- Error responses should include `error`, `message`, and `timestamp`

## Versioning
- Use `/api/v1/` for versioned endpoints

## Security
- Use authentication (JWT or session-based)
- Validate all inputs

