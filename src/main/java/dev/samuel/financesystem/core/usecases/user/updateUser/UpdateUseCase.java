package dev.samuel.financesystem.core.usecases.user.updateUser;

import dev.samuel.financesystem.core.entities.User;

public interface UpdateUseCase {

    User execute(Long id, User user);

}
