package com.example.taskmanager.service;

import com.example.taskmanager.exception.ResourceNotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@Service
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository repository;

    public TaskService(TaskRepository repository){
        this.repository = repository;
    }

    public List<Task> getAllTasks(){
        logger.info("Fetching all tasks");
        return repository.findAll();
    }

    public Task getTaskById(Long id) {
        logger.info("Fetching task with id: {}", id);
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found with ID:" + id));
    }

    public Task createTask(Task task){
        logger.info("Creating a new task");
        return repository.save(task);
    }

    public Task updateTask(Long id, Task updated){
        logger.info("Updating task with id: {}", id);
        return repository.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setDescription(updated.getDescription());
            existing.setCompleted(updated.isCompleted());
            return repository.save(existing);
        }).orElseThrow(() -> {
            logger.warn("Task with id {} not found", id);
            return new ResourceNotFoundException("Task not found with ID:" + id);
        });
    }

    public void deleteTask(Long id){
        logger.info("Deleting task {}", id);
        repository.deleteById(id);
    }
}