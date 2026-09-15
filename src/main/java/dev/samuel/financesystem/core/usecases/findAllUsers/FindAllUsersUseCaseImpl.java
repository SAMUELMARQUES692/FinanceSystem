package dev.samuel.financesystem.core.usecases.findAllUsers;

import dev.samuel.financesystem.core.entities.User;
import dev.samuel.financesystem.core.gateway.UserGateway;

import java.util.List;

public class FindAllUsersUseCaseImpl implements FindAllUsersUseCase{

    private final UserGateway userGateway;

    public FindAllUsersUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public List<User> execute() {
        return userGateway.findAllUsers();
    }
}
