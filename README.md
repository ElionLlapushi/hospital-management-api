# Hospital Management API

A RESTful Hospital Management System built with Java and Spring Boot.

## Features

- Manage Patients
- Manage Doctors
- Manage Appointments
- Manage Bills
- REST API endpoints
- SQLite database integration
- Maven project structure
- Docker support for deployment

## Technologies Used

- Java 17
- Spring Boot
- Maven
- SQLite
- Docker
- Git & GitHub

## API Endpoints

### Patients

- GET /api/patients
- GET /api/patients/{id}
- POST /api/patients
- PUT /api/patients/{id}
- DELETE /api/patients/{id}

### Doctors

- CRUD operations for doctors.

### Appointments

- Create and manage appointments.

### Bills

- Create and manage patient bills.

## Project Structure

```
src
 ├── controller
 ├── service
 ├── model
 ├── repository
 ├── config
 └── resources
```

## Getting Started

### Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/hospital-management-api.git
```

### Run the project

```bash
mvn spring-boot:run
```

## Future Improvements

- Authentication with JWT
- Role-based authorization
- Password encryption
- PostgreSQL support
- Swagger/OpenAPI documentation
- Unit and Integration Tests

## Author

**Elion Llapushi**
