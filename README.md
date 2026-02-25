# MovieVault

A full-stack movie review platform inspired by IMDb and Filmweb. Browse movies, write reviews, manage watchlists, and explore detailed statistics.

Built with **Spring Boot 3** (Java 21) + **React 18** (TypeScript) + **MariaDB**, fully containerized with Docker.

## Quick Start

```bash
docker compose up --build -d
```

The app will be available at **http://localhost**

## Demo Accounts

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@movievault.com | admin123 |
| Critic | roger@movievault.com | critic123 |
| Critic | mark@movievault.com | critic123 |
| User | jan@movievault.com | user123 |
| User | anna@movievault.com | user123 |

## Features

- Browse and search 50+ movies with real TMDB posters
- Filter by genre, sort by rating/date/title
- User registration and JWT authentication
- Write reviews and rate movies (1-10)
- Like/unlike reviews from other users
- Critic reviews (weighted differently from user reviews)
- Personal watchlist management
- User profile with avatar upload
- Dark/light theme toggle
- Admin dashboard with analytics (charts, stats)
- Admin user management (role changes, deletion)

## Tech Stack

**Backend:**
- Java 21, Spring Boot 3.4
- Spring Security + JWT
- Spring Data JPA + Hibernate
- MariaDB 11

**Frontend:**
- React 18, TypeScript, Vite
- Tabler UI (Bootstrap-based)
- ApexCharts for admin analytics
- Axios for API calls

**Infrastructure:**
- Docker + Docker Compose
- Nginx reverse proxy
- Multi-stage builds

## Project Structure

```
├── backend/          # Spring Boot API
│   └── src/main/java/com/movievault/
│       ├── controller/   # REST endpoints
│       ├── model/        # JPA entities
│       ├── repository/   # Data access
│       ├── service/      # Business logic
│       ├── security/     # JWT + auth
│       ├── seed/         # Database seeders
│       └── config/       # App configuration
├── frontend/         # React SPA
│   └── src/
│       ├── views/        # Page components
│       ├── components/   # Reusable UI
│       ├── contexts/     # React contexts
│       └── services/     # API client
└── docker-compose.yml
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/login | Login |
| POST | /api/auth/register | Register |
| GET | /api/movies | List movies (paginated, filterable) |
| GET | /api/movies/{id} | Movie details |
| GET | /api/movies/featured | Featured movies |
| GET | /api/reviews/movie/{id} | Reviews for a movie |
| POST | /api/reviews | Create review |
| GET | /api/watchlist | User's watchlist |
| POST | /api/watchlist | Add to watchlist |
| GET | /api/admin/analytics/* | Admin analytics |
| GET | /api/admin/users | Admin user list |
