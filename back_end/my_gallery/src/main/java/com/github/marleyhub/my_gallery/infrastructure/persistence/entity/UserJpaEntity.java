package com.github.marleyhub.my_gallery.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

/**
 * Driven Adapter: The JPA representation of a User.
 */
@Entity
@Table(name = "user")
public class UserJpaEntity {

    @Id
    private UUID id; // Keycloak Subject ID

    private long storageUsedInBytes;
    
    private long storageQuotaInBytes;
    
    private boolean active;

    // JPA requires a no-args constructor
    protected UserJpaEntity() {}

    public UserJpaEntity(UUID id, long storageUsedInBytes, long storageQuotaInBytes, boolean active) {
        this.id = id;
        this.storageUsedInBytes = storageUsedInBytes;
        this.storageQuotaInBytes = storageQuotaInBytes;
        this.active = active;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public long getStorageUsedInBytes() { return storageUsedInBytes; }
    public void setStorageUsedInBytes(long storageUsedInBytes) { this.storageUsedInBytes = storageUsedInBytes; }

    public long getStorageQuotaInBytes() { return storageQuotaInBytes; }
    public void setStorageQuotaInBytes(long storageQuotaInBytes) { this.storageQuotaInBytes = storageQuotaInBytes; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
