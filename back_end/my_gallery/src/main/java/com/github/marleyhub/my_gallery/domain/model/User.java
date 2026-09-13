package com.github.marleyhub.my_gallery.domain.model;

import java.util.UUID;

public class User {
    // 1GB default quota
    private static final long DEFAULT_QUOTA_BYTES = 1024L * 1024L * 1024L;

    private final UUID id;            // Maps to Keycloak's 'sub' claim (Subject ID)
    private long storageUsedInBytes;
    private long storageQuotaInBytes;
    private boolean active;

    public User(UUID id, long storageUsedInBytes, long storageQuotaInBytes, boolean active) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (storageUsedInBytes < 0) {
            throw new IllegalArgumentException("Storage used cannot be negative");
        }
        if (storageQuotaInBytes < 0) {
            throw new IllegalArgumentException("Storage quota cannot be negative");
        }

        this.id = id;
        this.storageUsedInBytes = storageUsedInBytes;
        this.storageQuotaInBytes = storageQuotaInBytes;
        this.active = active;
    }

    // Factory method for creating a brand new user upon first login
    public static User createNew(UUID keycloakId) {
        return new User(keycloakId, 0L, DEFAULT_QUOTA_BYTES, true);
    }

    // Business Logic
    public boolean canUpload(long fileSizeInBytes) {
        if (!active) {
            throw new IllegalStateException("Cannot upload media: User account is deactivated.");
        }
        return (storageUsedInBytes + fileSizeInBytes) <= storageQuotaInBytes;
    }

    public void recordUpload(long fileSizeInBytes) {
        if (!canUpload(fileSizeInBytes)) {
            throw new IllegalStateException("Storage quota exceeded.");
        }
        this.storageUsedInBytes += fileSizeInBytes;
    }

    public void recordDeletion(long fileSizeInBytes) {
        if (fileSizeInBytes < 0) {
            throw new IllegalArgumentException("File size cannot be negative");
        }
        this.storageUsedInBytes = Math.max(0, this.storageUsedInBytes - fileSizeInBytes);
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    // Getters
    public UUID getId() { return id; }
    public long getStorageUsedInBytes() { return storageUsedInBytes; }
    public long getStorageQuotaInBytes() { return storageQuotaInBytes; }
    public boolean isActive() { return active; }
}
