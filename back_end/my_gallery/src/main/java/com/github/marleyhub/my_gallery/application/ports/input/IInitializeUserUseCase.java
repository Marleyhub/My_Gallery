package com.github.marleyhub.my_gallery.application.ports.input;

import com.github.marleyhub.my_gallery.domain.model.User;
import java.util.UUID;

public interface IInitializeUserUseCase {
    User initializeNewUser(UUID keycloakId);
}
