# Instant Payment API

## Overview

An Instant Payment API service that allows users to send money instantly using REST. The focus is on high availability, transactional processing, and error handling.

## Features

- Balance validation before processing transactions
- Concurrency handling to prevent double spending
- PostgreSQL for transaction persistence
- Kafka for asynchronous transaction notifications
- Containerized with Docker for cloud deployment

## Tech Stack

- Java with Spring Boot
- Gradle as the build tool
- PostgreSQL as the database
- Kafka for event-driven notifications
- Docker for containerization

## Usage

### Build the project

```bash
gradle build -x test
```

### Run the application

```bash
docker compose up
```

## Next Steps & Improvements
- Integrate a database migration tool (e.g., Flyway)
- Implement authentication using JWT tokens with role-based access control
- Extract senderId from JWT tokens for transaction processing
- Enhance the payment process:
    - The client sends basic details (e.g., amount) to the backend to create an initial Payment entity.
    - The backend responds with form data, including a generated idempotency key.
    - The client submits the completed form using the fixed idempotency key, ensuring consistency regardless of the client implementation.
- Implement frontend part and consumer microservice for notifications
- Introduce a correlation identifier for Kafka messages
- Implement DTOs and mappers for GET endpoints
- Expand test coverage for reliability
- Provide comprehensive API documentation
