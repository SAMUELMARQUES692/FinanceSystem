package dev.samuel.financesystem.core.gateway;

import dev.samuel.financesystem.core.entities.Account;

public interface AccountGateway {

    Account createAccount(Account account);

    boolean existsByUserId(Long userId);

}
