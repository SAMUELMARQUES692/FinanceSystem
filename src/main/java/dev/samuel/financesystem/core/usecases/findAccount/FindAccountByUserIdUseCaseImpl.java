package dev.samuel.financesystem.core.usecases.findAccount;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.core.gateway.AccountGateway;

public class FindAccountByUserIdUseCaseImpl implements FindAccountByUserIdUseCase {


    private final AccountGateway accountGateway;

    public FindAccountByUserIdUseCaseImpl(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    @Override
    public Account execute(Long userId) {
        return accountGateway.findByUserId(userId);
    }
}
