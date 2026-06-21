package dev.samuel.financesystem.core.usecases.login;

import dev.samuel.financesystem.core.gateway.LoginGateway;

public class LoginUseCaseImpl implements LoginUseCase {

    private final LoginGateway loginGateway;

    public LoginUseCaseImpl(LoginGateway loginGateway) {
        this.loginGateway = loginGateway;
    }

    @Override
    public LoginOutput execute(LoginInput loginInput) {
        return loginGateway.login(loginInput);
    }
}
