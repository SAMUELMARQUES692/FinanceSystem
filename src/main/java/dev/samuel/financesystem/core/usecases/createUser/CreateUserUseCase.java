package dev.samuel.financesystem.core.usecases.createUser;

import dev.samuel.financesystem.core.entities.User;

public interface CreateUserUseCase {

    User execute(User user);
}
