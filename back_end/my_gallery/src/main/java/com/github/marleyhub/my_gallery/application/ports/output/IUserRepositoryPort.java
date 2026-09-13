package com.github.marleyhub.my_gallery.application.ports.output;

import com.github.marleyhub.my_gallery.domain.model.User;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepositoryPort {
    User save(User user);
    Optional<User> findById(UUID id);
}
