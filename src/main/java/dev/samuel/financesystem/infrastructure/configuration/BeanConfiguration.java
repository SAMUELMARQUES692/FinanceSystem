package dev.samuel.financesystem.infrastructure.configuration;

import dev.samuel.financesystem.core.gateway.*;
import dev.samuel.financesystem.core.usecases.account.createAccount.CreateAccountUseCase;
import dev.samuel.financesystem.core.usecases.account.createAccount.CreateAccountUseCaseImpl;
import dev.samuel.financesystem.core.usecases.account.findAccountById.FindByIdUseCase;
import dev.samuel.financesystem.core.usecases.account.findAccountById.FindByIdUseCaseImpl;
import dev.samuel.financesystem.core.usecases.user.createUser.CreateUserUseCase;
import dev.samuel.financesystem.core.usecases.user.createUser.CreateUserUseCaseImpl;
import dev.samuel.financesystem.core.usecases.user.deleteUser.DeleteUserUseCase;
import dev.samuel.financesystem.core.usecases.user.deleteUser.DeleteUserUseCaseImpl;
import dev.samuel.financesystem.core.usecases.account.findAccount.FindAccountByUserIdUseCaseImpl;
import dev.samuel.financesystem.core.usecases.account.findAccount.FindAccountByUserIdUseCase;
import dev.samuel.financesystem.core.usecases.user.findAllUsers.FindAllUsersUseCase;
import dev.samuel.financesystem.core.usecases.user.findAllUsers.FindAllUsersUseCaseImpl;
import dev.samuel.financesystem.core.usecases.scope.findScope.FindScopeByNameUseCase;
import dev.samuel.financesystem.core.usecases.scope.findScope.FindScopeByNameUseCaseImpl;
import dev.samuel.financesystem.core.usecases.user.findUser.FindByEmailUseCase;
import dev.samuel.financesystem.core.usecases.user.findUser.FindByEmailUseCaseImpl;
import dev.samuel.financesystem.core.usecases.login.LoginUseCase;
import dev.samuel.financesystem.core.usecases.login.LoginUseCaseImpl;
import dev.samuel.financesystem.core.usecases.transactions.reportUse.ReportUseCase;
import dev.samuel.financesystem.core.usecases.transactions.reportUse.ReportUseCaseImpl;
import dev.samuel.financesystem.core.usecases.transactions.transferUse.TransferUseCase;
import dev.samuel.financesystem.core.usecases.transactions.transferUse.TransferUseCaseImpl;
import dev.samuel.financesystem.core.usecases.account.updateAccount.UpdateAccountUseCase;
import dev.samuel.financesystem.core.usecases.account.updateAccount.UpdateAccountUseCaseImpl;
import dev.samuel.financesystem.core.usecases.user.updateUser.UpdateUseCase;
import dev.samuel.financesystem.core.usecases.user.updateUser.UpdateUseCaseImpl;
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

    @Bean
    public FindByEmailUseCase findByEmailUseCase(UserGateway userGateway) {
        return new FindByEmailUseCaseImpl(userGateway);
    }

    @Bean
    public FindAllUsersUseCase findAllUsersUseCase(UserGateway userGateway) {
        return new FindAllUsersUseCaseImpl(userGateway);
    }

    // Account
    @Bean
    public CreateAccountUseCase createAccountUseCase(AccountGateway accountGateway) {
        return new CreateAccountUseCaseImpl(accountGateway);
    }

    @Bean
    public FindAccountByUserIdUseCase findAccountByUserIdUseCase(AccountGateway accountGateway) {
        return new FindAccountByUserIdUseCaseImpl(accountGateway);
    }

    @Bean
    public UpdateAccountUseCase updateAccountUseCase(AccountGateway accountGateway) {
        return new UpdateAccountUseCaseImpl(accountGateway);
    }

    @Bean
    public FindByIdUseCase findByIdUseCase(AccountGateway accountGateway) {
        return new FindByIdUseCaseImpl(accountGateway);
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

    // Scopes
    @Bean
    public FindScopeByNameUseCase findScopeByNameUseCase(ScopeGateway scopeGateway) {
        return new FindScopeByNameUseCaseImpl(scopeGateway);
    }
}
