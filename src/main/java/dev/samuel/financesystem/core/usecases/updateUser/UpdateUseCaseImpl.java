package dev.samuel.financesystem.core.usecases.updateUser;

import dev.samuel.financesystem.core.entities.User;
import dev.samuel.financesystem.core.gateway.UserGateway;

public class UpdateUseCaseImpl implements UpdateUseCase {

    private final UserGateway userGateway;

    public UpdateUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public User execute(Long id, User user) {
        return userGateway.updateUser(id, user);
    }
}
