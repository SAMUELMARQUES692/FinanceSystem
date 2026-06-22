package dev.samuel.financesystem.core.usecases.deleteUser;

import dev.samuel.financesystem.core.gateway.UserGateway;

public class DeleteUserUseCaseImpl implements DeleteUserUseCase{

    private final UserGateway userGateway;

    public DeleteUserUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public void execute(Long id) {
        userGateway.deleteUser(id);
    }
}
