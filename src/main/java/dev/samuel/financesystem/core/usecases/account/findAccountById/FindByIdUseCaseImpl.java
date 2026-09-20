package dev.samuel.financesystem.core.usecases.account.findAccountById;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.core.gateway.AccountGateway;

public class FindByIdUseCaseImpl implements FindByIdUseCase {

    private final AccountGateway accountGateway;

    public FindByIdUseCaseImpl(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    @Override
    public Account execute(Long id) {
        return accountGateway.findById(id);
    }
}
