package dev.samuel.financesystem.core.usecases.findUser;

import dev.samuel.financesystem.core.entities.User;

public interface FindByEmailUseCase {

    User execute(String email);

}
