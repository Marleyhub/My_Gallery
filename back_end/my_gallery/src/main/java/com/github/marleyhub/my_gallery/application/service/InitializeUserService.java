package com.github.marleyhub.my_gallery.application.service;

import com.github.marleyhub.my_gallery.application.ports.input.IInitializeUserUseCase;
import com.github.marleyhub.my_gallery.application.ports.output.IUserRepositoryPort;
import com.github.marleyhub.my_gallery.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InitializeUserService implements IInitializeUserUseCase {

    private final IUserRepositoryPort userRepositoryPort;

    public InitializeUserService(IUserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public User initializeNewUser(UUID keycloakId) {
        if (userRepositoryPort.findById(keycloakId).isPresent()) {
            throw new IllegalStateException("User with ID " + keycloakId + " is already initialized.");
        }

        User newUser = User.createNew(keycloakId);
        return userRepositoryPort.save(newUser);
    }
}
