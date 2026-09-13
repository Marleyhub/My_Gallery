package com.github.marleyhub.my_gallery.infrastructure.persistence.mapper;

import com.github.marleyhub.my_gallery.domain.model.User;
import com.github.marleyhub.my_gallery.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

/**
 * Maps between the pure Domain Entity (User) and the Database Entity (UserJpaEntity).
 * This acts as the translation layer protecting the Domain from JPA.
 */
@Component
public class UserMapper {

    public UserJpaEntity toJpaEntity(User domainUser) {
        if (domainUser == null) return null;
        
        return new UserJpaEntity(
            domainUser.getId(),
            domainUser.getStorageUsedInBytes(),
            domainUser.getStorageQuotaInBytes(),
            domainUser.isActive()
        );
    }

    public User toDomain(UserJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        return new User(
            jpaEntity.getId(),
            jpaEntity.getStorageUsedInBytes(),
            jpaEntity.getStorageQuotaInBytes(),
            jpaEntity.isActive()
        );
    }
}
