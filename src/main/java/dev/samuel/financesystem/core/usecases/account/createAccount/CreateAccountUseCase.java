package dev.samuel.financesystem.core.usecases.account.createAccount;

import dev.samuel.financesystem.core.entities.Account;

public interface CreateAccountUseCase {

    Account execute(Account account);
}
