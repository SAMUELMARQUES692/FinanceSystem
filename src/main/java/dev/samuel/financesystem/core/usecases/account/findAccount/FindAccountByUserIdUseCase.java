package dev.samuel.financesystem.core.usecases.account.findAccount;

import dev.samuel.financesystem.core.entities.Account;

public interface FindAccountByUserIdUseCase {
    
    Account execute(Long userId);
}
