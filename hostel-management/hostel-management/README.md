# Hostel Management System (Spring Boot)

REST backend for managing a hostel: rooms, students, room allocation, monthly fees, complaints, and a dashboard.

**Stack:** Java 17, Spring Boot 3.3, Spring Web, Spring Data JPA, Spring Security (HTTP Basic), Bean Validation, H2 / MySQL, springdoc-openapi.

## Run
```bash
mvn spring-boot:run                                    # in-memory H2
mvn spring-boot:run -Dspring-boot.run.profiles=mysql   # MySQL (edit application-mysql.properties)
mvn test
```
- Swagger UI: http://localhost:8080/swagger-ui.html  (click "Authorize", use Basic auth)
- H2 console: http://localhost:8080/h2-console  (JDBC URL `jdbc:h2:mem:hostel`, user `sa`, empty password)

## Demo users
| user   | password  | role   | access                     |
|--------|-----------|--------|----------------------------|
| admin  | admin123  | ADMIN  | everything, incl. DELETE   |
| warden | warden123 | WARDEN | all /api/** except DELETE  |

## Endpoints
| Area | Endpoints |
|------|-----------|
| Rooms | `GET /api/rooms?availableOnly=true`, `GET/PUT/DELETE /api/rooms/{id}`, `POST /api/rooms` |
| Students | `GET /api/students?name=`, `GET/PUT/DELETE /api/students/{id}`, `POST /api/students` |
| Allocation | `POST /api/allocations`, `POST /api/allocations/{id}/checkout`, `GET /api/allocations?activeOnly=`, `GET /api/allocations/room/{roomId}` |
| Payments | `POST /api/payments`, `GET /api/payments`, `GET /api/payments/student/{id}` |
| Complaints | `POST /api/complaints`, `GET /api/complaints?status=`, `PATCH /api/complaints/{id}/status?status=RESOLVED` |
| Dashboard | `GET /api/dashboard` |

## Example
```bash
curl -u warden:warden123 -H "Content-Type: application/json" \
  -d '{"studentId":2,"roomId":2}' http://localhost:8080/api/allocations

curl -u warden:warden123 -H "Content-Type: application/json" \
  -d '{"studentId":1,"amount":3500,"forMonth":"2026-09","method":"UPI","reference":"TXN123"}' \
  http://localhost:8080/api/payments
```

## Business rules
- A student can hold only one active allocation; rooms cannot exceed capacity.
- Room capacity cannot be reduced below current occupancy; rooms/students with active residents can't be deleted.
- One fee record per student per month; payments require an active allocation.

## Ideas to extend
JWT auth with a users table, student self-service portal, visitor log, mess/attendance, PDF fee receipts, a React/Thymeleaf front end, Flyway migrations.
