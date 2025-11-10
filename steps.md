### 1. Initialize Spring Boot Project.

| Setting                 | Example Value       | Meaning                                       |
| ----------------------- | ------------------- | --------------------------------------------- |
| **Project**             | Maven Project       | Maven = build tool that manages dependencies. |
| **Language**            | Java                | Obviously.                                    |
| **Spring Boot Version** | 3.x (latest stable) | Gives you up-to-date features.                |
| **Group**               | `com.example`       | Like a namespace for your code.               |
| **Artifact**            | `taskmanager`       | This becomes your project folder name.        |
| **Name**                | `TaskManager`       | Display name.                                 |
| **Packaging**           | Jar                 | Typical for backend apps.                     |
| **Java version**        | 17                  | Modern and supported long-term.               |

Also add the dependencies:
- Spring Data JPA
- PostgreSQL Driver

### 2. Create the very first controller (an API endpoint). Re-run the code and go to http://localhost:8080/hello.
> com/example/taskmanager/controller/HelloController.java
```
package com.example.taskmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Task Manager backend!";
    }
}
```

### 3. These will be our CRUD (Create, Read, Update, Delete) API's.
| Action   | HTTP Method | Path          | Description             |
| -------- | ----------- | ------------- | ----------------------- |
| Create   | `POST`      | `/tasks`      | Add a new task          |
| Read all | `GET`       | `/tasks`      | Get list of all tasks   |
| Read one | `GET`       | `/tasks/{id}` | Get a specific task     |
| Update   | `PUT`       | `/tasks/{id}` | Update an existing task |
| Delete   | `DELETE`    | `/tasks/{id}` | Remove a task           |

### 4. Now lets add the support for PostgreSQL database.
- Open `src/main/resources/application.properties` and add the following:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
- ddl-auto=update → automatically creates/updates tables based on your classes.
- show-sql=true → prints SQL queries to your terminal (great for learning).

### 5. Create the `Task` entity in DB (using JPA, Java Persistence API)
- Java Persistent API is a specification - a set of rules telling how Java objects should be saved to and loaded from the database.
- Hibernate is used by default in Spring Boot to implement JPA.
> src/main/java/com/example/taskmanager/model/Task.java
```
package com.example.taskmanager.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data                   // Lombok: auto-generates getters/setters/toString/etc.
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private boolean completed = false;
}
```
- @Entity → Marks this class as a JPA entity (Maps to a DB table)
- @Id → mark it as a primary Key
- @GeneratedValue → auto-increment ID
- Lombok’s @Data → saves you from writing boilerplate (getters/setters).

When you run your app, JPA will create a task table automatically in your DB.

### 6. Create Repository (Data Access Layer)
> src/main/java/com/example/taskmanager/repository/TaskRepository.java
```
package com.example.taskmanager.repository;

import com.example.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
```
- You don’t have to write SQL.
- JpaRepository already gives you:
  - save(), findAll(), findById(), deleteById() — etc.

### 7. Create the Controller (API endpoints)
> src/main/java/com/example/taskmanager/controller/TaskController.java
```
package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return repository.save(task);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updated) {
        return repository.findById(id).map(task -> {
            task.setTitle(updated.getTitle());
            task.setDescription(updated.getDescription());
            task.setCompleted(updated.isCompleted());
            return repository.save(task);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
```
| Annotation                                                        | Purpose                                                |
| ----------------------------------------------------------------- | ------------------------------------------------------ |
| `@RestController`                                                 | Tells Spring this class handles HTTP requests          |
| `@RequestMapping("/tasks")`                                       | Base URL for all endpoints inside                      |
| `@GetMapping` / `@PostMapping` / `@PutMapping` / `@DeleteMapping` | Map to HTTP methods                                    |
| `@RequestBody`                                                    | Automatically converts JSON from request → Java object |
| `@PathVariable`                                                   | Extracts `{id}` part from URL                          |

### 8. Summary (Now Test with Postman or Curl)
| Layer               | Purpose             | Example                  |
| ------------------- | ------------------- | ------------------------ |
| **Model**           | Represents DB table | `Task.java`              |
| **Repository**      | Talks to DB         | `TaskRepository`         |
| **Controller**      | Handles API calls   | `TaskController`         |
| **Spring Boot App** | Boots everything    | `TaskmanagerApplication` |

`Frontend API Call -> Controller(calls correct CRUD operation) -> Repository(Performs the operation by interacting with DB) -> Returns the response -> Controller -> Frontend.
<img width="1756" height="1000" alt="image" src="https://github.com/user-attachments/assets/13150c90-1308-4ac9-a43e-028e7be24865" />

### 9. Create a separate service to interact with the repository(modify controller as well)
> src/main/java/com/example/taskmanager/service/TaskService.java
```
package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository){
        this.repository = repository;
    }

    public List<Task> getAllTasks(){
        return repository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return repository.findById(id);
    }

    public Task createTask(Task task){
        return repository.save(task);
    }

    public Task updateTask(Long id, Task updated){
        return repository.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            existing.setCompleted(updated.isCompleted());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Task Not Found"));
    }

    public void deleteTask(Long id){
        repository.deleteById(id);
    }
}
```
- Frontend(Postman) -> Controller -> Service -> Repository -> DB Access -> Repository -> Service -> Controller -> Frontend(Postman)

### 10. What if something wrong happens?
- Add a custom exception and a global exception handler
> src/main/java/com/example/taskmanager/exception/ResourceNotFoundException.java
```
package com.example.taskmanager.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```
- Update the service class(getTaskById or updateTask etc.) to throw `new ResourceNotFoundException` instead of `new RuntimeException` with our custom, nice message.
> src/main/java/com/example/taskmanager/exception/GlobalExceptionHandler.java
```
package com.example.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp",LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false));

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp",LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        body.put("path", request.getDescription(false));

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
```
- How will we(developers) know that something wrong has happened?..Add simple logging using `slf4j` in the `TaskService` class.
```
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// --------- Declaration
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
// Use
   public Task getTaskById(Long id) {
        logger.info("Fetching task with id: {}", id);
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with ID:" + id));
    }
```
