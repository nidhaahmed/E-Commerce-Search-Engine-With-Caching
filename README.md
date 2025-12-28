# E-Commerce Search Engine with Ranking & Caching

A scalable e-commerce search backend designed to handle millions of products with fast, relevant search results using Elasticsearch, Redis caching, and Spring Boot.

## Tech Stack

- Backend: Java 21, Spring Boot, Spring Data JPA, Spring Security
- Search: Elasticsearch 8.x
- Cache: Redis
- Database: PostgreSQL
- Frontend: React + TypeScript + Tailwind
- Infra: Docker, Docker Compose

## Architecture

```
Client
  → Backend (Spring Boot)
    → Redis (cache)
    → Elasticsearch (search)
    → PostgreSQL (metadata)
```

## How to Run (Local)

### Prerequisites

- Docker
- Docker Compose

### Start Everything

```bash
docker compose up
```

### Services

- **Backend**: http://localhost:8080
- **Health**: http://localhost:8080/actuator/health
- **Elasticsearch**: http://localhost:9200
- **Frontend**: http://localhost:3000
