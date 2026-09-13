package com.github.marleyhub.my_gallery.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Album {
    private final UUID id;
    private final UUID ownerId;
    private String title;
    private String description;
    private final LocalDateTime createdAt;

    public Album(UUID id, UUID ownerId, String title, String description, LocalDateTime createdAt) {
        if (id == null || ownerId == null) {
            throw new IllegalArgumentException("Album must have an ID and an Owner ID");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Album title cannot be empty");
        }
        
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.description = description; // Description can be null or empty
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    // Factory method for creating a brand new album
    public static Album createNew(UUID ownerId, String title, String description) {
        return new Album(UUID.randomUUID(), ownerId, title, description, LocalDateTime.now());
    }

    // Business Logic: Updating title
    public void rename(String newTitle) {
        if (newTitle == null || newTitle.isBlank()) {
            throw new IllegalArgumentException("Album title cannot be empty");
        }
        this.title = newTitle;
    }

    // Business Logic: Updating description
    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getOwnerId() { return ownerId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
