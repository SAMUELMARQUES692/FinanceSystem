package dev.samuel.financesystem.infrastructure.configuration;

import dev.samuel.financesystem.core.gateway.LoginGateway;
import dev.samuel.financesystem.core.gateway.UserGateway;
import dev.samuel.financesystem.core.usecases.createUser.CreateUserUseCase;
import dev.samuel.financesystem.core.usecases.createUser.CreateUserUseCaseImpl;
import dev.samuel.financesystem.core.usecases.login.LoginUseCase;
import dev.samuel.financesystem.core.usecases.login.LoginUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public LoginUseCase loginUseCase(LoginGateway loginGateway) {
        return new LoginUseCaseImpl(loginGateway);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(UserGateway userGateway) {
        return new CreateUserUseCaseImpl(userGateway);
    }

}
