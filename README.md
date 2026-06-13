[![Build](https://img.shields.io/endpoint?url=https://gist.githubusercontent.com/v1rex/27dc15f2c2aeef4b021fdff63d7ba722/raw/lift-nexus-metrics.json&style=for-the-badge&logo=github&logoColor=white)](https://github.com/V1rex/lift-nexus-api/actions/workflows/ci.yml)
[![Test Coverage](https://img.shields.io/endpoint?url=https://gist.githubusercontent.com/v1rex/27dc15f2c2aeef4b021fdff63d7ba722/raw/lift-nexus-coverage.json?v=1&style=for-the-badge&logo=github-actions&logoColor=white)](https://v1rex.github.io/lift-nexus-api/coverage/)
![License: Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=for-the-badge)

<br />
<div align="center">
  <a href="https://github.com/v1rex/lift-nexus-api">
    <img src="docs/assets/logo_horizental_without_gradient_slim.png" alt="Lift Nexus API Logo">
  </a>

  <h3 align="center">Lift Nexus API</h3>

  <p align="center">
    Spring Boot backend MVP for warehouse dispatch optimization.<br />
    Built to explore domain modeling, async job handling, PostgreSQL/Flyway persistence,
    integration testing, and Timefold-based constraint solving.
    <br />
    <br />
    <a href="https://lift-nexus.amine-bahij.dev/"><strong>Read the docs »</strong></a>
    &middot;
    <a href="#getting-started">Getting started</a>
    &middot;
    <a href="https://github.com/v1rex/lift-nexus-api/issues">Roadmap</a>
  </p>
</div>

---

> **Status:** Portfolio / learning project. The current focus is a static dispatching MVP, not a production warehouse management system.

## About

**Lift Nexus API** models a simplified warehouse dispatching scenario where transport orders need to be assigned to forklifts.

The project goes beyond a basic CRUD API by combining:
- a warehouse domain model for forklifts, load units, storage bins, and transport orders
- asynchronous optimization jobs with status tracking
- Timefold Solver for constraint-based assignment planning
- PostgreSQL persistence with Flyway migrations
- OpenAPI documentation and Docker-based local setup
- unit and integration tests with JUnit 5 and Testcontainers

The goal is to experiment with backend architecture and optimization in a realistic intralogistics domain.

[Read the full documentation](https://lift-nexus.amine-bahij.dev/)

## Tech Stack
![Java 21+](https://img.shields.io/badge/Java-21+-ED8936?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Timefold](https://img.shields.io/badge/Timefold-2.0.0--beta-blue?style=for-the-badge&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white)

![JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI+PHBhdGggZmlsbD0id2hpdGUiIGQ9Ik0xMiAyYy01LjUyMyAwLTEwIDQuNDc3LTEwIDEwczQuNDc3IDEwIDEwIDEwIDEwLTQuNDc3IDEwLTEwLTQuNDc3LTEwLTEwLTEwem0wIDE4Yy00LjQxIDAtOC0zLjU5LTgtOHMzLjU5LTggOC04IDggMy41OSA4IDgtMy41OSA4LTggOHptMy41LTljLS4yOC0uNDUtLjczLS43NS0xLjI1LS43NS0uODI4IDAtMS41LjY3Mi0xLjUgMS41cy42NzIgMS41IDEuNSAxLjUuOTcyLS4zIDEuMjUtLjc1Ii8+PC9zdmc+)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

![JUnit5](https://img.shields.io/badge/JUnit%205-25A162?style=for-the-badge&logoColor=white)
![Testcontainers](https://img.shields.io/badge/Testcontainers-092E20?style=for-the-badge&logoColor=white)
![Spotless](https://img.shields.io/badge/Spotless-333333?style=for-the-badge&logoColor=white)
![JaCoCo](https://img.shields.io/badge/JaCoCo-4285F4?style=for-the-badge&logoColor=white)

## Features

- Manage forklifts, load units, storage bins, and transport orders through REST endpoints
- Start dispatch optimization as an asynchronous job
- Poll job status and retrieve optimization results
- Apply initial hard and soft constraints for forklift-to-order assignment planning
- Run locally with Docker Compose
- Validate database changes through Flyway migrations
- Generate and inspect API documentation through Swagger UI

## Getting Started

### Prerequisites

For the recommended setup:

```bash
docker --version
docker compose version
```

For local development without running the app container:

```bash
java -version
./mvnw -version
```

### Run with Docker Compose

```bash
git clone https://github.com/v1rex/lift-nexus-api.git
cd lift-nexus-api
docker compose up -d
```

The API should be available at:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Hosted docs: `https://lift-nexus.amine-bahij.dev/`

Stop the environment:

```bash
docker compose down
```

### Development Mode

Run PostgreSQL in Docker and start the application from your IDE or terminal:

```bash
docker compose up -d db
./mvnw clean spring-boot:run
```

## Testing and Code Quality

```bash
./mvnw clean test            # Unit tests
./mvnw clean verify          # Full verification, including integration tests
./mvnw spotless:check        # Formatting check
./mvnw spotless:apply        # Apply formatting
```

Coverage report:

```bash
open target/site/jacoco/index.html
```

## Documentation

The full project documentation is available here:

**[lift-nexus.amine-bahij.dev](https://lift-nexus.amine-bahij.dev/)**

It includes the project overview, architecture, domain model, optimization approach, API usage, testing strategy, known limitations, and roadmap.

For release notes, see [CHANGELOG.md](./CHANGELOG.md).

## Current Scope and Limitations

Lift Nexus API is an MVP / portfolio project, not a production warehouse management system yet.

Main limitations:
- No authentication or authorization yet
- Simplified warehouse topology and pathfinding
- Limited solver constraints
- Basic observability only
- Not tested in a production-like deployment environment yet

See the full [documentation](https://lift-nexus.amine-bahij.dev/site/limitations/) for detailed limitations and planned improvements.

## Roadmap

| Milestone | Status       | Focus |
|----------|--------------|-------|
| Core MVP - Static Dispatching | Released in v0.1.0 | Physical warehouse model and one-shot optimization |
| Dynamic Dispatching | Planned      | React to warehouse changes instead of running only one-shot dispatching |
| Production Constraints | Planned      | Add more realistic planning constraints such as deadlines, priorities, equipment compatibility, and energy-aware dispatching |
| Auth, Monitoring, Deployment | Planned      | Improve security, observability, and deployment readiness |
| Performance/Benchmarking | Planned      | Measure behavior under larger scenarios |

See the [open issues](https://github.com/v1rex/lift-nexus-api/issues) and the [project roadmap](https://lift-nexus.amine-bahij.dev/roadmap/) for more details.

## What I Learned

I built this project to go beyond simple CRUD applications and practice backend architecture in a more realistic optimization-driven domain.

Coming from an academic optimization background, where I previously worked with mathematical modeling in GurobiPy, this project helped me understand how optimization can be integrated into a real backend application using Timefold.

Through this project, I learned and applied:

- domain modeling for warehouse dispatching
- asynchronous job handling
- database migrations with Flyway
- integration testing with PostgreSQL and Testcontainers
- separating API, service, persistence, and planning concerns
- constraint solving with Timefold
- documenting architectural trade-offs and limitations

For more context, read my article: [Why I am building Lift Nexus API](https://amine-bahij.dev/writing/why-i-am-building-lift-nexus-api/).


## License

Distributed under the Apache License 2.0. See [LICENSE](./LICENSE) for more information.

```text
Copyright 2026 Mohamed Amine Bahij
```

## Contact

**Amine Bahij**

- GitHub: [@v1rex](https://github.com/v1rex)
- Email: contact@amine-bahij.dev
- Project: [https://github.com/v1rex/lift-nexus-api](https://github.com/v1rex/lift-nexus-api)

## Acknowledgments

- [Timefold](https://timefold.ai/) – Constraint solver
- [Spring Boot](https://spring.io/projects/spring-boot) – Java application framework
- [PostgreSQL](https://www.postgresql.org/) – Relational database
- [Flyway](https://flywaydb.org/) – Database migrations
