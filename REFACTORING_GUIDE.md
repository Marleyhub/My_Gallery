# Refactoring Guide: My Gallery

This guide outlines the steps to refactor the **My Gallery** project (Java 21 + Spring Boot & Angular) to adopt Clean Architecture/Hexagonal Architecture (DIP), and to integrate Keycloak for Identity and Access Management (IAM) and Traefik for reverse proxy, load balancing, and TLS.

## 1. Architectural Restructuring (Clean / Hexagonal Architecture)

The goal is to apply the Dependency Inversion Principle (DIP). The inner layers (Domain) should not depend on outer layers (Frameworks, Databases, UI).

### Backend (`back_end/my_gallery`)

**Current State:** Standard layered architecture (Controllers -> Services -> Repositories -> Entities).
**Target State:** Ports and Adapters (Domain, Application, Infrastructure, UI).

- [ ] **Define the Domain Layer:**
  - Move core business logic and models (e.g., `User`, `MediaItem`) into a pure `domain/model` package.
  - Remove all Spring or JPA annotations (e.g., `@Entity`, `@Table`) from domain models. They should be POJOs.
  - Define custom domain exceptions.
- [ ] **Define Ports (Interfaces):**
  - **Inbound Ports:** Use Cases (e.g., `UploadImageUseCase`, `CreateUserUseCase`). These are interfaces implemented by the application layer.
  - **Outbound Ports:** Repositories and external service interfaces (e.g., `UserRepositoryPort`, `StorageServicePort`). These are interfaces used by the application layer and implemented by the infrastructure layer.
- [ ] **Implement the Application Layer:**
  - Create services that implement the Inbound Ports (Use Cases).
  - These services orchestrate domain objects and call Outbound Ports.
- [ ] **Implement the Infrastructure Layer (Adapters):**
  - **Persistence Adapter:** Create JPA entities (e.g., `UserJpaEntity`) and Spring Data repositories. Implement the `UserRepositoryPort` to map between Domain Models and JPA Entities.
  - **Storage Adapter:** Refactor `S3Service` to implement `StorageServicePort`.
  - **Security Adapter:** Implement Keycloak integration here (replacing the current custom JWT/BCrypt logic).
- [ ] **Implement the Presentation Layer (Adapters):**
  - Refactor `Controllers` to inject Inbound Ports (Use Cases) instead of standard services.
  - Use DTOs for request and response mapping, keeping domain models internal.

### Frontend (`front_end/my-app`)

- [ ] **Module Reorganization:**
  - Adopt a domain-driven folder structure (e.g., `features/gallery`, `features/auth`, `core`, `shared`).
- [ ] **Core & Shared:**
  - `core`: Singletons, HTTP interceptors (for adding Keycloak tokens), guards.
  - `shared`: Reusable UI components, pipes, directives.
- [ ] **State Management & Services:**
  - Separate API communication (infrastructure) from UI state management. Use interfaces for services if dependency injection swapping is anticipated.

---

## 2. Centralized IAM with Keycloak

Migrate from the custom `JwtService` and `BCrypt` implementation to Keycloak.

- [ ] **Keycloak Setup (Docker):**
  - Add Keycloak to `docker-compose.yml`.
  - Create a Realm for "My Gallery".
  - Configure a Client for the Angular frontend (Public Client or PKCE).
  - Configure a Client for the Spring Boot backend (Bearer-only / Resource Server).
- [ ] **Backend Integration:**
  - Remove `JwtService`, `JwtAuthFilter`, `JwtAuthenticationFilter`, and `AuthController`.
  - Add `spring-boot-starter-oauth2-resource-server`.
  - Configure `SecurityConfig` to validate Keycloak-issued JWTs.
  - Ensure roles/scopes are mapped correctly for authorization.
- [ ] **Frontend Integration:**
  - Integrate a library like `keycloak-angular` or `angular-oauth2-oidc`.
  - Remove the custom `login-page` and `register-page` forms. Redirect users to the Keycloak login/registration pages.
  - Configure HTTP Interceptors to attach the Keycloak Bearer token to API requests.

---

## 3. Reverse Proxy & TLS with Traefik

Introduce Traefik to route traffic, manage SSL certificates, and handle load balancing.

- [ ] **Traefik Setup (Docker):**
  - Add Traefik service to `docker-compose.yml`.
  - Configure entrypoints (port 80 for HTTP, 443 for HTTPS).
- [ ] **Service Routing (Labels):**
  - **Frontend:** Add Traefik labels to the Angular container to route traffic (e.g., `Host(mygallery.local)`).
  - **Backend:** Add Traefik labels to route API requests (e.g., `Host(api.mygallery.local)` or path-based `/api`).
  - **Keycloak:** Add Traefik labels for Keycloak (e.g., `Host(auth.mygallery.local)`).
- [ ] **TLS Configuration:**
  - For local development, use tools like `mkcert` to generate local trusted certificates and provide them to Traefik.
  - For production, configure Traefik with Let's Encrypt for automatic certificate generation.
- [ ] **Network Isolation:**
  - Ensure only Traefik exposes ports to the host machine. Backend, Frontend, Keycloak, and databases should communicate over an internal Docker network.

---

## 4. Java 21 & Spring Boot Best Practices

To elevate your proficiency in the Java ecosystem, this refactoring will strictly adhere to modern Java 21 and Spring Boot 3.x best practices:

### Java 21 Features
- **Records for DTOs:** Use `record` for all Data Transfer Objects (DTOs) and Events. Records provide built-in immutability, concise syntax, and automatically generate `equals()`, `hashCode()`, and `toString()`.
- **Virtual Threads (Project Loom):** Enable Virtual Threads in Spring Boot (`spring.threads.virtual.enabled=true`) for highly scalable, lightweight concurrency, especially useful for blocking I/O operations like S3 uploads.
- **Pattern Matching & Switch Expressions:** Utilize enhanced `switch` expressions and pattern matching for cleaner conditional logic, replacing complex `if-else` chains.
- **Sealed Classes:** Use `sealed` classes and interfaces in the Domain layer to strictly control the hierarchy of domain models and custom exceptions.

### Spring Boot & Architecture Best Practices
- **Constructor Injection:** Avoid `@Autowired` on fields. Use constructor injection (often facilitated by Lombok's `@RequiredArgsConstructor` or simply explicit constructors) to ensure dependencies are immutable and classes are easily testable.
- **Global Exception Handling:** Use `@ControllerAdvice` and `@ExceptionHandler` in the Presentation layer to globally catch domain and infrastructure exceptions, translating them into standardized HTTP problem details (RFC 7807).
- **MapStruct for Mapping:** Replace manual mapping logic (e.g., `UserMapper.java`) with **MapStruct**. It generates high-performance, compile-time mapping code between Domain Models, Entities, and DTOs.
- **Immutability First:** Design domain models to be as immutable as possible. State changes should ideally return new instances or be tightly controlled through specific domain methods (Rich Domain Model), rather than simple getters and setters (Anemic Domain Model).
- **Validation:** Use `spring-boot-starter-validation` (Jakarta Bean Validation) on inbound DTOs in the Presentation layer to fail fast on bad input, keeping validation logic out of the core domain unless it's a strict business rule.

---

## Recommended Order of Execution

1. **Infrastructure Prep:** Add Keycloak and Traefik to `docker-compose.yml` and get the basic routing and authentication flows working manually.
2. **Backend Architecture Refactor:** Restructure the Spring Boot app into Domain/Ports/Adapters.
3. **Backend IAM Migration:** Swap the custom JWT implementation with Spring Security OAuth2 Resource Server pointing to Keycloak.
4. **Frontend Architecture & IAM:** Reorganize the Angular app and integrate Keycloak for authentication.
5. **Final Polish:** Ensure all Traefik routes and TLS certificates are correctly configured for local development.
