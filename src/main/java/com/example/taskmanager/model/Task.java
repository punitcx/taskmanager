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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
}

