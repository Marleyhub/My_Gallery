package com.github.marleyhub.my_gallery.infrastructure.persistence.repository;

import com.github.marleyhub.my_gallery.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Driven Adapter: The standard Spring Data interface.
 * It talks strictly in terms of UserJpaEntity, never the pure Domain User.
 */
@Repository
public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, UUID> {
}
