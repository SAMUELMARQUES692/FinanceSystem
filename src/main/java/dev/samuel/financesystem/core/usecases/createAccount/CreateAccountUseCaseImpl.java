package dev.samuel.financesystem.core.usecases.createAccount;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.core.gateway.AccountGateway;
import dev.samuel.financesystem.infrastructure.exception.AccountAlreadyExistsException;

public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    private final AccountGateway accountGateway;

    public CreateAccountUseCaseImpl(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    @Override
    public Account execute(Account account) {
        if (accountGateway.existsByUserId(account.userId())) {
            throw new AccountAlreadyExistsException("User already has an account");
        }
        return accountGateway.createAccount(account);
    }

}
