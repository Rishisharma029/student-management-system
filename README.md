# 🎓 Student Management & Attendance System

A full-stack web application for managing students, subjects, and attendance tracking.

**Stack:** Spring Boot 3.2 · Java 17 · MySQL 8 · React 18 · Vite

---

## 🚀 Quick Start

### Prerequisites

| Tool | Version |
|------|---------|
| Java | 17+ |
| Maven | 3.8+ |
| Node.js | 18+ |
| MySQL | 8.0+ |

---

### Step 1 — Database Setup

```sql
-- Open MySQL and run:
source database/schema.sql
source database/sample-data.sql
```

This creates the `student_management_db` database with all tables and sample data.

---

### Step 2 — Backend Setup

```bash
cd backend
```

Create your local config file:
```bash
copy src\main\resources\application-local.properties.example src\main\resources\application-local.properties
```

Edit `application-local.properties` and set your MySQL credentials:
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD_HERE
```

Start the backend:
```bash
mvn spring-boot:run
```

Backend runs at: **http://localhost:8080**  
Swagger UI: **http://localhost:8080/swagger-ui.html**

---

### Step 3 — Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

Frontend runs at: **http://localhost:5173**

---

## 📱 Application Features

- ✅ **Student Management** — Create, view, update, delete students
- ✅ **Subject Management** — Manage course subjects
- ✅ **Attendance Tracking** — Mark and view daily attendance
- ✅ **Attendance Analytics** — Automatic percentage calculation
- ✅ **Search & Filter** — Search students by name, email, roll number
- ✅ **Dashboard** — Live statistics from the database
- ✅ **API Documentation** — Interactive Swagger UI

---

## 📁 Project Structure

```
student-management-system/
├── backend/                    ← Spring Boot API
│   └── src/main/java/com/example/studentmanagement/
│       ├── controller/         ← REST endpoints
│       ├── service/            ← Business logic
│       ├── repository/         ← Database queries
│       ├── model/              ← JPA entities
│       ├── dto/                ← Data transfer objects
│       ├── exception/          ← Custom exceptions + handler
│       └── config/             ← CORS, Swagger, etc.
├── frontend/                   ← React + Vite
│   └── src/
│       ├── api/                ← Axios API calls
│       ├── components/         ← Reusable React components
│       └── pages/              ← Page-level components
├── database/
│   ├── schema.sql              ← Table definitions
│   └── sample-data.sql         ← Test data
└── docs/
    ├── API.md                  ← API documentation
    ├── HINTS.md                ← Task hints (no spoilers)
    ├── TASKS.md                ← Implementation tasks
    └── DEVELOPMENT_RULES.md    ← Code standards
```

---

## 🔒 Environment Variables

Never commit real credentials. Use `application-local.properties` (git-ignored).

```properties
# application-local.properties
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
```

---

## 🧪 Running Tests

```bash
cd backend
mvn test
```

Tests use an H2 in-memory database — no MySQL needed for testing.
