# Task-Manager
My first backend microservices application as I transition to SDE.

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
    - `service/TaskService.Java` (The repository handling part created as a separate service)

Nov 7:
- Tested the functionalities using **Postman**
