package dev.samuel.financesystem.core.usecases.user.findUser;

import dev.samuel.financesystem.core.entities.User;

public interface FindByEmailUseCase {

    User execute(String email);

}
