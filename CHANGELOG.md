# Changelog

All notable changes to Lift Nexus API are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project follows [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [0.1.0] - 2026-06-14

### Added

* Initial static warehouse dispatching MVP.
* Warehouse domain model for forklifts, forklift types, storage bins, load units, and transport orders.
* REST APIs for managing the current warehouse state.
* Asynchronous dispatch job workflow with job status tracking.
* Timefold Solver integration for simplified forklift-to-order assignment and sequencing.
* Initial hard constraints for forklift capacity and equipment compatibility.
* Initial soft constraint for reducing estimated travel distance.
* PostgreSQL persistence with Flyway-based schema migrations.
* Docker Compose setup for local development.
* OpenAPI/Swagger documentation for API exploration.
* MkDocs-based project documentation.
* JUnit 5 and Testcontainers-based testing setup.
* JaCoCo test coverage reporting.
* GitHub Actions workflow for build, test, documentation, and report generation.

### Changed

* Prepared the project documentation for the first application-ready release.
* Improved README structure, project scope, limitations, and roadmap presentation.
* Clarified that the current system is a portfolio MVP and not a production warehouse management system.
* Documented the current static dispatching scope and known limitations.
* Disabled Checkstyle as a temporary release blocker for `v0.1.0`.

### Removed

* Removed unreliable Checkstyle-based documentation coverage reporting from the release pipeline.
* Removed the documentation coverage badge from the README.

### Known Limitations

* The system is not production-ready.
* No authentication or authorization is implemented yet.
* Dispatching is currently static and based on a warehouse state snapshot.
* The warehouse topology and distance model are simplified.
* The current constraint set is intentionally limited.
* Observability is basic.
* No production deployment setup is included yet.
* The project has not been tested in a production-like environment.

### Notes

This release marks the first stable portfolio milestone of Lift Nexus API.

The goal of `v0.1.0` is to provide a clean and explainable backend MVP that demonstrates how optimization logic can be integrated into a Spring Boot application with persistence, asynchronous jobs, tests, documentation, and CI-generated reports.

Future work will continue in Milestone 2, with a focus on dynamic dispatching, better solver orchestration, and reintroducing Checkstyle as a proper quality gate.
