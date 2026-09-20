package dev.samuel.financesystem.core.usecases.account.findAccountByPix;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.core.gateway.AccountGateway;

public class FindAccountByPixUseCaseImpl implements FindAccountByPixUseCase {

    private final AccountGateway accountGateway;

    public FindAccountByPixUseCaseImpl(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    @Override
    public Account execute(String pix) {
        return accountGateway.findByPix(pix);
    }
}
