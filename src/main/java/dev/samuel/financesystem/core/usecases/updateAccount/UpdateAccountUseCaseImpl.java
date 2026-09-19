package dev.samuel.financesystem.core.usecases.updateAccount;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.core.gateway.AccountGateway;

public class UpdateAccountUseCaseImpl implements UpdateAccountUseCase {

    private final AccountGateway accountGateway;

    public UpdateAccountUseCaseImpl(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    @Override
    public Account execute(Long id, Account account) {
        return accountGateway.updateAccount(id, account);
    }
}
