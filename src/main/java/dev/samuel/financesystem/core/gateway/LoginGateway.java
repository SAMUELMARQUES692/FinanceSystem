package dev.samuel.financesystem.core.gateway;

import dev.samuel.financesystem.core.usecases.login.LoginInput;
import dev.samuel.financesystem.core.usecases.login.LoginOutput;

public interface LoginGateway {

    LoginOutput login(LoginInput loginInput);
}
