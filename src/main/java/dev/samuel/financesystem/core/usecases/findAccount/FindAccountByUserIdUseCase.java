package dev.samuel.financesystem.core.usecases.findAccount;

import dev.samuel.financesystem.core.entities.Account;

public interface FindAccountByUserIdUseCase {
    
    Account execute(Long userId);
}
