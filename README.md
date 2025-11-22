# Task-Manager
A multi-user task management system backend application made with Spring Boot and Java based tech stack.
**Progress:**

Nov 3:
- Learnt about Frontend, CRUD Operations, REST API, Backend (Java, Spring Boot), JPA, Lombok, Postman, JWT, CI/CD, Docker, Microservices.
- Initialized the project and created a Hello Controller(/hello).
- Added `com/example/taskmanager/`:
    - `/model/Task.java` (A `Task` class mapped to DB table using JPA annotation)
    - `/repository/TaskRepository.java` (To perform database handling directly using `JpaRepository` interface methods, which handles the SQL queries for common CRUD operations internally)
    - `/controller/TaskController.java` (Different CRUD operations functionalities via registered APIs)
    - `TaskManagerApplication.java` (Performs booting and setting up everything)
 
Nov 4:
<img width="1793" height="1035" alt="image" src="https://github.com/user-attachments/assets/ec818965-af5a-43d9-aec5-75b91f63cae3" />

Nov 5:
- Studied the JPA annotations: @Entity, @Data, @NoArgsConstructor, @AllArgsConstructor, @Id, @GeneratedValue, @RestController, @RequestMapping, @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @RequestBody, @PathVariable etc...

Nov 6:
- Added `com/example/taskmanager/`:
    - `service/TaskService.Java` (Separated the service part from controller to interact with repository)

Nov 7:
- Tested the functionalities using **Postman**
- Studied MVC Architecture

Nov 11:
- Added exception handling with `com/example/taskmanager/`:
    - `exception/ResourceNotFoundException.java`
    - `exception/GlobalExceptionHandler.java`
- Added simple `slf4j` logging for info and warn messages (in `TaskService.java`)
  <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/c07ba85f-b2c5-4b47-bd41-e91f6b2b3fcf" />
- Added DTOs:
    - `dto/TaskRequestDTO`
    - `dto/TaskResponseDTO`
- Added data validation using `@Valid` annotation on incoming data(`TaskRequestDTO`)
- Updated the global exception handler to handle data validation exception (`MethodArgumentNotValidException`)

Nov 12-16:
- Added User and UserRepository for multi-user app support.
- Added email, passwordHash, role fields.
