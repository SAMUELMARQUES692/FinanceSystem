package dev.samuel.financesystem.core.gateway;

import dev.samuel.financesystem.core.entities.Account;

public interface AccountGateway {

    Account createAccount(Account account);

    boolean existsByUserId(Long userId);

    Account findByUserId(Long userId);

    Account getBalance(Long userId);

    Account updateAccount(Long id, Account account);

    Account findById(Long id);

    Account findByPix(String pix);

}
