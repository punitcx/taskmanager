package com.example.taskmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstHello {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Task Manager backend (Spring Boot, without any database)!";
    }
}

