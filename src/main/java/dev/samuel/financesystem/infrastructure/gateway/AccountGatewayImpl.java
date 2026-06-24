package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.core.gateway.AccountGateway;
import dev.samuel.financesystem.infrastructure.mapper.AccountMapper;
import dev.samuel.financesystem.infrastructure.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountGatewayImpl implements AccountGateway {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;


    @Override
    public Account createAccount(Account account) {
       dev.samuel.financesystem.infrastructure.persistence.Account persistenceAccount = accountMapper.toPersistenceEntity(account);
       dev.samuel.financesystem.infrastructure.persistence.Account saved = accountRepository.save(persistenceAccount);
        return accountMapper.toDomain(saved);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return accountRepository.existsByUserId(userId);
    }

    @Override
    public Account findByUserId(Long userId) {
        return accountRepository.findByUserId(userId)
                .map(accountMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

}
