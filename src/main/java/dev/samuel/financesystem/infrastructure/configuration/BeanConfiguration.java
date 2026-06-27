package dev.samuel.financesystem.infrastructure.configuration;

import dev.samuel.financesystem.core.gateway.AccountGateway;
import dev.samuel.financesystem.core.gateway.LoginGateway;
import dev.samuel.financesystem.core.gateway.TransactionGateway;
import dev.samuel.financesystem.core.gateway.UserGateway;
import dev.samuel.financesystem.core.usecases.createAccount.CreateAccountUseCase;
import dev.samuel.financesystem.core.usecases.createAccount.CreateAccountUseCaseImpl;
import dev.samuel.financesystem.core.usecases.createUser.CreateUserUseCase;
import dev.samuel.financesystem.core.usecases.createUser.CreateUserUseCaseImpl;
import dev.samuel.financesystem.core.usecases.deleteUser.DeleteUserUseCase;
import dev.samuel.financesystem.core.usecases.deleteUser.DeleteUserUseCaseImpl;
import dev.samuel.financesystem.core.usecases.login.LoginUseCase;
import dev.samuel.financesystem.core.usecases.login.LoginUseCaseImpl;
import dev.samuel.financesystem.core.usecases.reportUse.ReportUseCase;
import dev.samuel.financesystem.core.usecases.reportUse.ReportUseCaseImpl;
import dev.samuel.financesystem.core.usecases.transferUse.TransferUseCase;
import dev.samuel.financesystem.core.usecases.transferUse.TransferUseCaseImpl;
import dev.samuel.financesystem.core.usecases.updateUser.UpdateUseCase;
import dev.samuel.financesystem.core.usecases.updateUser.UpdateUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    // User
    @Bean
    public LoginUseCase loginUseCase(LoginGateway loginGateway) {
        return new LoginUseCaseImpl(loginGateway);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(UserGateway userGateway) {
        return new CreateUserUseCaseImpl(userGateway);
    }

    @Bean
    public UpdateUseCase updateUseCase(UserGateway userGateway) {
        return new UpdateUseCaseImpl(userGateway);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserGateway userGateway) {
        return new DeleteUserUseCaseImpl(userGateway);
    }

    // Account
    @Bean
    public CreateAccountUseCase createAccountUseCase(AccountGateway accountGateway) {
        return new CreateAccountUseCaseImpl(accountGateway);
    }

    // Trasactions
    @Bean
    public TransferUseCase transferUseCase(TransactionGateway transactionGateway) {
        return new TransferUseCaseImpl(transactionGateway);
    }

    @Bean
    public ReportUseCase reportUseCase(TransactionGateway transactionGateway) {
        return new ReportUseCaseImpl(transactionGateway);
    }
}
