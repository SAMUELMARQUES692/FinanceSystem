package dev.samuel.financesystem.core.usecases.user.findAllUsers;

import dev.samuel.financesystem.core.entities.User;

import java.util.List;

public interface FindAllUsersUseCase {

    List<User> execute();
}
