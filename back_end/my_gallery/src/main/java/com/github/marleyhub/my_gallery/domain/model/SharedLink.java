package com.github.marleyhub.my_gallery.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class SharedLink {
    private final String token; // The secure random string in the URL
    private final UUID targetId; // The ID of the MediaItem or Album being shared
    private final UUID ownerId; // The user who generated the link
    private LocalDateTime expiresAt;
    private boolean active;

    public SharedLink(String token, UUID targetId, UUID ownerId, LocalDateTime expiresAt, boolean active) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token cannot be empty");
        }
        if (targetId == null || ownerId == null) {
            throw new IllegalArgumentException("Target ID and Owner ID cannot be null");
        }

        this.token = token;
        this.targetId = targetId;
        this.ownerId = ownerId;
        this.expiresAt = expiresAt;
        this.active = active;
    }

    // Factory method for generating a new link that expires in X days
    public static SharedLink generateFor(UUID targetId, UUID ownerId, int validForDays) {
        // In a real app, use a secure random generator for the token
        String secureToken = UUID.randomUUID().toString().replace("-", "");
        LocalDateTime expiration = LocalDateTime.now().plusDays(validForDays);
        return new SharedLink(secureToken, targetId, ownerId, expiration, true);
    }

    // Business Logic: Check if link is still valid for external visitors
    public boolean isValid() {
        if (!active) {
            return false;
        }
        // If expiresAt is null, it means it never expires. Otherwise, check if it's past the current time.
        if (expiresAt != null && LocalDateTime.now().isAfter(expiresAt)) {
            return false;
        }
        return true;
    }

    // Business Logic: The owner manually revokes the link before it expires
    public void revoke() {
        this.active = false;
    }

    // Business Logic: The owner extends the expiration date
    public void extendExpiration(int additionalDays) {
        if (!this.active) {
            throw new IllegalStateException("Cannot extend expiration of a revoked link.");
        }
        if (this.expiresAt == null) {
            this.expiresAt = LocalDateTime.now().plusDays(additionalDays);
        } else {
            this.expiresAt = this.expiresAt.plusDays(additionalDays);
        }
    }

    // Getters
    public String getToken() { return token; }
    public UUID getTargetId() { return targetId; }
    public UUID getOwnerId() { return ownerId; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public boolean isActive() { return active; }
}
