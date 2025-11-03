# Task-Manager
My first backend microservices application as I transition to SDE.

Progress:

Nov 3:
- Learnt about Frontend, CRUD Operations, REST API, Backend (Java, Spring Boot), JPA, Lombok, Postman, JWT, CI/CD, Docker, Microservices.
- Initialized the project and created a Hello Controller(/hello).
- Added `com/example/taskmanager/`:
    - `/model/Task.java` (A `Task` class mapped to DB table using JPA annotation)
    - `/repository/TaskRepository.java` (To perform data access from SQL table directly using `JpaRepository` class methods)
    - `/controller/TaskController.java` (Different CRUD operations functionalities via registered APIs)
