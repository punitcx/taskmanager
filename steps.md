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
- Open src/main/resources/application.properties and add the following:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
- ddl-auto=update → automatically creates/updates tables based on your classes.
- show-sql=true → prints SQL queries to your terminal (great for learning).

### Create the `Task` entity in DB (using JPA, Java Persistence API)
- Java Persistent API is used to map Java objects to SQL tables.
> Task.java
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
