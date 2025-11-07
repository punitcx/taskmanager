package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return service.createTask(task);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return service.getTaskById(id).orElseThrow(() -> new RuntimeException("Task Not Found"));
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updated) {
        return service.updateTask(id, updated);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
    }
}

