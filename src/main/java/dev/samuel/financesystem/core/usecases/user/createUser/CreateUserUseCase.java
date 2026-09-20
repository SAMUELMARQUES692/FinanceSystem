package dev.samuel.financesystem.core.usecases.user.createUser;

import dev.samuel.financesystem.core.entities.User;

public interface CreateUserUseCase {

    User execute(User user);
}
