package com.github.marleyhub.my_gallery.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class MediaItem {
    private static final long MAX_FILE_SIZE_BYTES = 500 * 1024 * 1024; 

    private final UUID id;
    private final UUID ownerId;
    private String filename;
    private final long sizeInBytes;
    private final String mimeType;
    private final LocalDateTime uploadedAt;

    public MediaItem(UUID id, UUID ownerId, String filename, long sizeInBytes, String mimeType, LocalDateTime uploadedAt) {
        if (id == null) {
            throw new IllegalArgumentException("MediaItem ID cannot be null");
        }
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner ID cannot be null");
        }
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }
        if (sizeInBytes <= 0 || sizeInBytes > MAX_FILE_SIZE_BYTES) {
            throw new IllegalArgumentException("Media size is invalid or exceeds maximum permitted size");
        }
        if (mimeType == null || mimeType.isBlank()) {
            throw new IllegalArgumentException("MIME type cannot be empty");
        }

        this.id = id;
        this.ownerId = ownerId;
        this.filename = filename;
        this.sizeInBytes = sizeInBytes;
        this.mimeType = mimeType;
        this.uploadedAt = uploadedAt;
    }

    // Business Logic
    public void rename(String newFilename) {
        if (newFilename == null || newFilename.isBlank()) {
            throw new IllegalArgumentException("New filename cannot be empty");
        }
        this.filename = newFilename;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getOwnerId() { return ownerId; }
    public String getFilename() { return filename; }
    public long getSizeInBytes() { return sizeInBytes; }
    public String getMimeType() { return mimeType; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
}