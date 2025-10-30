package com.coveragex.todo.Dto;

import java.time.Instant;

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private Instant createdAt;

    public TaskResponse(Long id, String title, String description, boolean completed, Instant createdAt) {
        this.id = id; this.title = title; this.description = description;
        this.completed = completed; this.createdAt = createdAt;
    }
    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    public Instant getCreatedAt() { return createdAt; }
}
