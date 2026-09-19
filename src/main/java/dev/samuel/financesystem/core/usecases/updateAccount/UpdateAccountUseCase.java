package dev.samuel.financesystem.core.usecases.updateAccount;


import dev.samuel.financesystem.core.entities.Account;

public interface UpdateAccountUseCase {

    Account execute(Long id, Account account);
}
