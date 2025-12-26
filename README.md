## Local Development (Docker)

Services required:
- PostgreSQL
- Elasticsearch
- Redis

All services are started using Docker Compose.

## High-Level Architecture

Client
  |
  v
Spring Boot API
  |        |        |
Postgres  Redis  Elasticsearch
