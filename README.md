# ElmerGram

ElmerGram is a backend REST API inspired by Instagram, built as an internship learning project.
The project focuses on backend architecture, authentication, authorization, pagination, and clean API design using Spring Boot.

This project is inspired by Instagram but is not a full clone.

---

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL (Supabase)
- JWT Authentication
- Maven

---

## What I Learned

- Building RESTful APIs using Spring Boot
- Designing layered backend architecture (Controller, Service, Repository)
- Implementing JWT-based authentication
- Role-based authorization using Spring Security (`@PreAuthorize`)
- Centralized global exception handling
- Using DTOs to control API input/output
- Implementing pagination with Spring Data (`Pageable`)
- Integrating a cloud-hosted PostgreSQL database (Supabase)
- Managing configuration using environment variables

---

## Key Features

- User registration and login
- JWT authentication and role-based access (ADMIN / USER)
- User profiles and posts
- Paginated responses for posts and users
- Global exception handling with consistent API responses
- Explorer/feed-style post retrieval

---

## Project Structure

- **Controllers**
  - Handle HTTP requests
  - Validate input
  - Return consistent API responses

- **DTOs**
  - Define request and response payloads
  - Prevent exposing entity models directly

- **Services**
  - Contain business logic
  - Handle authentication, authorization, and validation

- **Repos**
  - Data access layer using Spring Data JPA

- **globalExceptionHandling**
  - Centralized exception handling
  - Standardized error responses

---

## API Overview

### Authentication
Base URL: `/api/v1/auth`

- `POST /register`
  - Register a new user

- `POST /login`
  - Authenticate user and return JWT token

---

### Users
Base URL: `/api/v1/users`

- `GET /`
  - Get all users  
  - Requires `ADMIN` role

- `GET /{username}`
  - Get user profile by username

- `GET /{username}/posts`
  - Get posts created by a user
  - Supports pagination (`pageNumber`, `pageSize`)

- `PATCH /{username}`
  - Update user information

- `DELETE /{username}`
  - Delete a user  
  - Requires `ADMIN` role

---

### Posts
Base URL: `/api/v1/posts`

- `GET /{postId}`
  - Get post details by ID

- `POST /`
  - Create a new post

- `GET /explorer`
  - Get explorer/feed posts
  - Supports pagination (`pageNumber`, `pageSize`)

---

## Pagination

Pagination is implemented using Spring Data `Pageable`.

Common query parameters:
- `pageNumber` (default: 1)
- `pageSize` (default: 10)

---

## Configuration

The application uses environment variables:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SECRET_JWT_KEY`

JWT expiration is set to 12 hours.

---

## Notes

This project was built for learning purposes during an internship.
The focus is on backend fundamentals, clean architecture, and security concepts rather than production readiness.

---

## License

Educational use only.
