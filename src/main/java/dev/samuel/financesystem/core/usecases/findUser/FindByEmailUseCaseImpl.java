package dev.samuel.financesystem.core.usecases.findUser;

import dev.samuel.financesystem.core.entities.User;
import dev.samuel.financesystem.core.gateway.UserGateway;

public class FindByEmailUseCaseImpl implements FindByEmailUseCase{

    private final UserGateway userGateway;

    public FindByEmailUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public User execute(String email) {
        return userGateway.findByEmail(email);
    }
}
