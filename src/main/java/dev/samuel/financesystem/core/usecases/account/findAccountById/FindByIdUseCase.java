package dev.samuel.financesystem.core.usecases.account.findAccountById;

import dev.samuel.financesystem.core.entities.Account;

public interface FindByIdUseCase {

    Account execute(Long id);
}
