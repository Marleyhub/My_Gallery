package com.github.marleyhub.my_gallery.infrastructure.persistence.repository;

import com.github.marleyhub.my_gallery.application.ports.output.IUserRepositoryPort;
import com.github.marleyhub.my_gallery.domain.model.User;
import com.github.marleyhub.my_gallery.infrastructure.persistence.entity.UserJpaEntity;
import com.github.marleyhub.my_gallery.infrastructure.persistence.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Driven Adapter: This is the actual implementation of the Output Port.
 * It bridges the gap between the Application layer and the Spring Data layer.
 */
@Component
public class UserRepositoryAdapter implements IUserRepositoryPort {

    private final SpringDataUserRepository jpaRepository;
    private final UserMapper userMapper;

    public UserRepositoryAdapter(SpringDataUserRepository jpaRepository, UserMapper userMapper) {
        this.jpaRepository = jpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        // 1. Translate Domain Model to JPA Entity
        UserJpaEntity entityToSave = userMapper.toJpaEntity(user);
        
        // 2. Perform Database Operation
        UserJpaEntity savedEntity = jpaRepository.save(entityToSave);
        
        // 3. Translate saved JPA Entity back to Domain Model
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(UUID id) {
        // Find in DB, then map to domain if present
        return jpaRepository.findById(id)
                .map(userMapper::toDomain);
    }
}
