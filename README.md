# 🧑‍💼 Employee Management System

This is a **Spring Boot** based Employee Management System designed to perform **CRUD operations**, implement **role-based access**, monitor endpoints using **Spring Boot Actuator**, and support **Docker deployment**.

---

## 🚀 Features

- Create, Read, Update, Delete employees
- REST API using Spring Boot
- Role-based authentication using Spring Security
- API documentation using OpenAPI (Swagger)
- Logging with AOP
- Dockerized backend using Dockerfile and Docker Compose
- Environment-specific configurations with profiles

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot**
    - Spring Web
    - Spring Data JPA
    - Spring Security
    - Spring Boot Actuator
- **PostgreSQL**
- **Lombok**
- **OpenAPI / Swagger**
- **Docker & Docker Compose**

---

## 📁 Project Structure

employee-management/
├── src/
│   ├── main/
│   │   ├── java/com/employee_management/
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── service/
│   │   │   ├── config/
│   │   │   ├── aop/
│   │   └── resources/
│   │       ├── application.properties
├── pom.xml
├── Dockerfile
├── docker-compose.yml
└── README.md

