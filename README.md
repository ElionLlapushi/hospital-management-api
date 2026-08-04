<div align="center">

# 🏥 Hospital Management System — REST API

**A production-ready, secure backend service for managing hospital patient records, built with Java and Spring Boot.**

![Java](https://img.shields.io/badge/Java-17-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-Stateless%20Auth-6DB33F?logo=springsecurity)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

</div>

---

## 🏛️ Architectural Overview & Design

This system is built from the ground up following a layered architecture, separating concerns between the client, security, business logic, and data persistence layers:

```
[ Client / SPA Frontend ]
        │  (HTTPS / Bearer Token)
        ▼
[ Spring Security Filter Chain ] ──── (Stateless Auth & RBAC)
        │
        ▼
[ REST Controllers ] ──── (Jakarta Validation & DTOs)
        │
        ▼
[ Service Layer ] ──── (Core Domain & Business Logic)
        │
        ▼
[ Data Access Objects (DAO) ] ──── (PreparedStatement / JDBC)
        │
        ▼
[ Relational Database (SQLite / Production RDBMS) ]
```

---

## ⚡ Core Technical Features

- **Stateless Token-Based Authentication** — secure login flow with no server-side session state.
- **Rigid Input Sanitization & Validation** — enforced via Jakarta Bean Validation on all incoming DTOs.
- **SQL Injection Resiliency** — 100% parameterized queries via `PreparedStatement`, no raw string concatenation.
- **Real-time Business Telemetry** — endpoints exposing live operational statistics (e.g. daily patient stats).
- **Cross-Origin Resource Sharing (CORS)** — configurable policy for safe frontend integration.
- **Containerized Deployment** — packaged and runnable via Docker for consistent environments.

---

## 📚 RESTful API Specification

### Authentication

| Method | Endpoint             | Description                    | Access |
|--------|-----------------------|---------------------------------|--------|
| `POST` | `/api/auth/login`     | Authenticates a user and issues a token | Public |

### Patients Module

| Method   | Endpoint                    | Description                          | Access        |
|----------|------------------------------|---------------------------------------|---------------|
| `GET`    | `/api/patients`              | Retrieves all registered patients     | Authenticated |
| `GET`    | `/api/patients/{id}`         | Fetches a single patient by ID        | Authenticated |
| `GET`    | `/api/patients/stats/today`  | Returns today's patient statistics    | Authenticated |
| `POST`   | `/api/patients`              | Registers a new patient               | Authenticated |
| `PUT`    | `/api/patients/{id}`         | Updates an existing patient's record  | Authenticated |
| `DELETE` | `/api/patients/{id}`         | Soft-deletes / removes a patient      | Authenticated |

---

## 📂 Modular Package Structure

```text
src/main/java/com/hospitalmanagement/hospital/management/api/
│
├── config/           # Security, CORS, and application-wide configuration
├── controller/        # REST endpoints, request/response handling
├── dao/                # Data Access Layer (JDBC / PreparedStatement)
├── model/              # Domain entities and DTOs
├── exception/          # Custom exceptions & global exception handling
└── HospitalApplication.java   # Spring Boot application entry point
```

---

## ⚙️ Getting Started & Local Setup

### Prerequisites

- Java Development Kit (JDK 17+)
- Apache Maven 3.8+
- Git

### 1. Clone the Repository

```bash
git clone https://github.com/ElionLlapushi/hospital-management.git
cd hospital-management-api
```

### 2. Configure Environment Properties

Navigate to `src/main/resources/application.properties` and verify your runtime bindings:

```properties
server.port=8081
spring.datasource.url=jdbc:sqlite:hospital.db
# Add your custom JWT secret keys and other environment-specific values here
```

### 3. Build and Run via Maven

```bash
mvn clean install
mvn spring-boot:run
```

### 4. Containerize with Docker

To build and spin up an isolated container instance:

```bash
docker build -t hospital-api .
docker run -p 8081:8081 hospital-api
```

---

## 🚀 Roadmap & Future Enterprise Enhancements

- [ ] **Role-Based Access Control (RBAC):** Restricting route access explicitly via `@PreAuthorize("hasRole('ADMIN')")`.
- [ ] **Comprehensive Audit Logging:** Tracking system mutations to an immutable audit ledger (`audit_logs`).
- [ ] **OpenAPI / Swagger Integration:** Interactive API documentation generation.
- [ ] **Automated Testing Suite:** JUnit 5 and Mockito test implementations for controller and service layers.

---

## 👤 Author

**Elion Llapushi**
* Epoka Software Engineering Student 