# Java Hexagonal Architecture (DIP) Refactoring Plan

This document details the exact changes needed to convert your current layered Spring Boot application into a **Dependency Inversion Principle (DIP)** based architecture. 

**The Power of DIP:** The fundamental power of the Dependency Inversion Principle relies on polymorphism. By using abstract interfaces, we invert source code dependencies, decoupling high-level core business logic (immutable enterprise rules) from low-level infrastructure details (databases, ORMs, HTTP delivery). This decoupling makes components like databases, UIs, and cloud providers (AWS) interchangeable plugins. It ensures the core source code is highly stable, resistant to external framework changes, and effortlessly testable. Inverting the source code dependency while maintaining the runtime flow of control is a mandatory pillar of scalable, long-lived software architecture.

## 1. Package Restructuring

Based on these principles, the project will be restructured into four distinct root packages to strictly enforce dependency boundaries.

```text
com.github.marleyhub.my_gallery
├── domain/              # Level 0 - Core: Enterprise Business Rules & Core Entities
│   ├── model/           # Entities and Value Objects (User, UserId)
│   └── exceptions/      # Custom Domain Exceptions
├── application/         # Level 1: Application Business Rules / Use Cases
│   ├── ports/           
│   │   ├── input/       # Service / UseCase interfaces exposed to drivers
│   │   └── output/      # Interfaces for repositories & third-party services
│   ├── service/         # Application Services orchestrating domain objects
│   └── dto/             # Application-level DTOs for safe boundary passing
├── infrastructure/      # Level 2 - Adapter: Frameworks, Drivers, & External Adapters
│   ├── persistence/     # Spring Data JPA integration
│   │   ├── entity/      # JPA Entities
│   │   ├── repository/  # Spring Data Repositories & Output Port Implementations
│   │   └── mapper/      # MapStruct mappers (Domain <-> JPA)
│   ├── security/        # Spring Security & IAM Implementation (Keycloak/JWT)
│   ├── aws/             # AWS S3 Storage Services Output Port Implementations
│   └── config/          # Spring Infrastructure Configuration (Beans)
└── presentation/        # Level 2 - Adapter: Controllers / REST Adapters
    ├── controller/      # REST Controllers (Input Port consumers)
    ├── request/         # Inbound HTTP payloads
    ├── response/        # Outbound HTTP payloads
    └── exception/       # Global ControllerAdvice & Error Handlers
```

---

## 2. Why was the Presentation Layer grouped inside Infrastructure originally?

In classic Hexagonal Architecture (Ports and Adapters by Alistair Cockburn), there are only two conceptual regions: the **Inside** (Domain/Application) and the **Outside** (Adapters). 

Because REST Controllers (HTTP Delivery) and JPA Repositories (Database Persistence) are *both* external adapters dealing with I/O and frameworks, they are technically on the same architectural level (Level 2). Thus, some implementations group them both under an `infrastructure` or `adapter` root folder (e.g., `infrastructure/in/web` vs `infrastructure/out/persistence`).

However, **your proposed structure is often preferred** and is highly recommended for modern Spring Boot applications. By separating `presentation` and `infrastructure` at the root level, you explicitly distinguish between **Driving Adapters** (things that trigger the application, like REST controllers in `presentation`) and **Driven Adapters** (things the application triggers, like databases in `infrastructure`). This provides cleaner mental mapping for developers reading the root project structure.

---

## 3. The Layers Detailed

### 1. Domain Layer (`domain/`)
**Hierarchy Rank:** Highest (Level 0 - Core)
**Dependencies:** Zero external imports. Pure Java (JDK) only. No Spring, no JPA, no Jackson.

*   Defines Enterprise Business Rules, core state, and invariants.
*   Houses Entities and Value Objects.
*   Enforces self-contained validation logic.
*   Defines custom business exceptions.

```java
package com.github.marleyhub.my_gallery.domain.model;

import java.util.UUID;

// Pure Java POJO. Zero frameworks.
public class User {
    private UUID id;
    private String username;
    private String email;
    // Business logic and invariant enforcement methods go here
}
```

### 2. Application Layer (`application/`)
**Hierarchy Rank:** Level 1
**Dependencies:** Imports `domain` and standard Java only. No frameworks.

*   Defines specific workflow operations (Use Cases).
*   Declares Input Ports (Use Case interfaces).
*   Declares Output Ports (Interfaces for DB/Storage).
*   Houses Services that implement Input Ports and call Output Ports.

```java
package com.github.marleyhub.my_gallery.application.ports.output;

import com.github.marleyhub.my_gallery.domain.model.User;
import java.util.Optional;

// Application dictates how data is stored, Infrastructure implements it.
public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(String id);
}
```

### 3. Infrastructure Layer (`infrastructure/`)
**Hierarchy Rank:** Level 2 (Driven Adapter)
**Dependencies:** Imports `domain`, `application`, and frameworks (Spring Data, AWS SDK).

*   Implements the Output Ports defined in the Application layer.
*   Handles persistent database interactions (JPA, Schema mapping).
*   Integrates external systems (AWS S3).

```java
package com.github.marleyhub.my_gallery.infrastructure.persistence.repository;

import com.github.marleyhub.my_gallery.application.ports.output.UserRepositoryPort;
import com.github.marleyhub.my_gallery.domain.model.User;
import com.github.marleyhub.my_gallery.infrastructure.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

// Implements the Output Port using Spring Data and JPA
@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final SpringDataUserRepository jpaRepository;
    private final UserMapper mapper;

    @Override
    public User save(User user) {
        UserJpaEntity entity = mapper.toEntity(user);
        return mapper.toDomain(jpaRepository.save(entity));
    }
}
```

### 4. Presentation Layer (`presentation/`)
**Hierarchy Rank:** Level 2 (Driving Adapter)
**Dependencies:** Imports `domain`, `application`, and delivery frameworks (Spring Web).

*   Acts as the HTTP delivery mechanism.
*   Parses HTTP requests into Application DTOs.
*   Calls Application Input Ports (Use Cases).
*   Translates responses/exceptions into HTTP status codes.

```java
package com.github.marleyhub.my_gallery.presentation.controller;

import com.github.marleyhub.my_gallery.application.ports.input.ManageUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    // Injects the Input Port (Use Case), NEVER the concrete service.
    private final ManageUserUseCase manageUserUseCase;

    @PostMapping
    public Object createUser(@RequestBody Object request) {
        // ... routing logic triggering manageUserUseCase
        return null;
    }
}
```
