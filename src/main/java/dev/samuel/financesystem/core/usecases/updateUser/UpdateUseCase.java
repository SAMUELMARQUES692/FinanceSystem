package dev.samuel.financesystem.core.usecases.updateUser;

import dev.samuel.financesystem.core.entities.User;

public interface UpdateUseCase {

    User execute(Long id, User user);

}
