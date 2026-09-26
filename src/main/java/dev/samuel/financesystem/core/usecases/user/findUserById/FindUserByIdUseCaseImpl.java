package dev.samuel.financesystem.core.usecases.user.findUserById;

import dev.samuel.financesystem.core.entities.User;
import dev.samuel.financesystem.core.gateway.UserGateway;

public class FindUserByIdUseCaseImpl implements FindUserByIdUseCase {

    private final UserGateway userGateway;

    public FindUserByIdUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public User execute(Long id) {
        return userGateway.findUserById(id);
    }
}
