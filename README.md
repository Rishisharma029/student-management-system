# 🎓 Student Management System - Training Edition

Welcome to the **Student Management System** backend repository! This isn't just an ordinary codebase—this is a custom-built, highly structured interactive Java & Spring Boot learning environment designed exclusively by Rishi, for Khushi.

---

## 👩‍💻 A Message for Khushi

If you're reading this, welcome to your Java backend training ground! 🚀 

This repository simulates a real-world, production-ready Spring Boot application. However, the `main` branch you are currently on has **38 specific features missing** (marked with `// TODO [JAVA-XX]`). 

Your mission is to hunt down these TODOs, write the missing Java code, make the automated tests pass, and bring the application to life.

---

# 🚦 START HERE

Before you touch any code, please review your personalized onboarding guides:

1. 🏁 **[Welcome & Instructions](./docs/START_HERE.md)** - *Read this first!*
2. 🐙 **[GitHub & Actions Guide](./docs/GITHUB_FOR_KHUSHI.md)** - *How to push and check your work.*
3. 🗺️ **[Learning Roadmap](./docs/TASK-ROADMAP.md)** - *The exact order to complete tasks.*
4. 📁 **[Quick File Map](./docs/FILE-MAP.md)** - *Where to find specific files quickly.*
5. ✅ **[Progress Tracker](./docs/PROGRESS.md)** - *Mark tasks off as you complete them!*

---

## 🔀 The Dual-Branch Learning System

This repository uses a strict two-branch system designed specifically for your learning. **You will work entirely on `main`.**

```mermaid
flowchart TD
    Repo[One Repository] --> Main[Branch: main]
    Repo --> Sol[Branch: rishi-solutions]
    
    Main --> KH[Khushi Works Here]
    KH --> TODO[Contains TODO Tasks]
    TODO --> Try[You try solving it first]
    Try --> Test[Run tests & debug]
    
    Sol --> RS[Rishi Implemented This]
    RS --> Complete[Contains Complete Code]
    Complete --> Explain[Easy Java Solutions]
    Explain --> Why[Problem + Why + How explained]
    
    Test --> Compare{Compare your code<br>with the solution}
    Why --> Compare
    Compare --> Understand((Understand & Learn!))
    
    style Main fill:#e1f5fe,stroke:#03a9f4,stroke-width:2px,color:#000
    style Sol fill:#e8f5e9,stroke:#4caf50,stroke-width:2px,color:#000
```

1. **Attempt:** Find a `[JAVA-XX]` task in the code on `main`. Write the implementation.
2. **Test:** Run `.\mvnw.cmd clean test` in your terminal. If it fails, debug it!
3. **Compare:** Switch to the `rishi-solutions` branch. There, you'll find a massive library of detailed explanations explaining exactly *how* I solved it, *why* I did it that way, and what the edge cases are.

---

## 🏗️ System Architecture

This application strictly follows the standard Spring Boot layered architecture to ensure separation of concerns.

```mermaid
flowchart TD
    Client(("React Frontend / Postman")) <-->|"JSON / REST"| Controller["Controller Layer<br>@RestController"]
    
    subgraph Spring Boot Backend
        Controller <-->|"DTOs"| Service["Service Layer<br>@Service"]
        Service <-->|"Entities"| Repository["Repository Layer<br>@Repository"]
        
        Service -.-> Exceptions["GlobalExceptionHandler<br>@RestControllerAdvice"]
        Controller -.-> Exceptions
    end
    
    Repository <-->|"Hibernate / JPA"| DB[("MySQL Database")]
    
    classDef layer fill:#f9f,stroke:#333,stroke-width:2px,color:#000;
    class Controller,Service,Repository,Exceptions layer;
```

---

## 💻 Tech Stack

- **Language:** Java 17+
- **Framework:** Spring Boot 3.x
- **Database:** MySQL 8.x (Production) & H2 (In-memory testing)
- **Data Access:** Spring Data JPA / Hibernate
- **Validation:** Jakarta Bean Validation
- **Boilerplate Reduction:** Lombok
- **Testing:** JUnit 5 & Mockito

---

## 🚀 Getting Started

### Prerequisites
- JDK 17 installed and added to your PATH.
- MySQL installed and running on port 3306.
- A MySQL database created named `student_management_db`.

### Running the Application

1. Open your terminal in the `backend` directory.
2. Update `src/main/resources/application-local.properties` with your MySQL username and password.
3. Start the application:
   ```bash
   .\mvnw.cmd spring-boot:run
   ```
4. Check the API documentation automatically generated for you at: 
   `http://localhost:8080/swagger-ui.html`

### Running the Tests
To verify your work on the tasks, run:
```bash
.\mvnw.cmd clean test
```

---

## ⚖️ License
This repository is strictly protected by an Exclusive Personal Use License. It is intended solely for the educational advancement of Khushi. See `LICENSE` for the complete binding restrictions.
