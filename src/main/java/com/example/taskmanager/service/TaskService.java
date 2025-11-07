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