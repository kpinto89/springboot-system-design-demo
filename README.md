# Spring Boot System Design Playground

A small Spring Boot project to practice core system design ideas in code.

## What this project demonstrates

- Controller-level rate limiting (token bucket)
- Service-layer delegation
- Async background processing with `@Async`
- Stateless API design basics

## Tech stack

- Java 17+ (project target in `pom.xml`)
- Spring Boot 3.2.4
- Maven

## Default server port

The app is configured to run on:

- `http://localhost:8082`

Source of truth:

- `src/main/resources/application.properties` (`server.port=8082`)

## Run the project

### Option 1: One-click in IntelliJ

A shared run configuration is already included:

- `.run/SystemDesignApplication.run.xml`

In IntelliJ:

1. Select `SystemDesignApplication` in the run configuration dropdown.
2. Click Run.

### Option 2: Run with Maven

```powershell
Set-Location "C:\Users\t_kevinpin\IdeaProjects\springboot-system-design-demo"
mvn spring-boot:run
```

If your default Java is below 17, set a JDK 17+ first, then run Maven.

## Test the project

```powershell
Set-Location "C:\Users\t_kevinpin\IdeaProjects\springboot-system-design-demo"
mvn test
```

## API

### Create order

- Method: `POST`
- URL: `http://localhost:8082/api/orders`
- Body:

```json
{
  "id": "ord-1",
  "product": "Keyboard",
  "quantity": 2
}
```

PowerShell example:

```powershell
Invoke-RestMethod -Method Post -Uri "http://localhost:8082/api/orders" -ContentType "application/json" -Body '{"id":"ord-1","product":"Keyboard","quantity":2}'
```

Possible responses:

- `200 OK` -> `Order accepted`
- `429 Too Many Requests` -> `Too many requests`
