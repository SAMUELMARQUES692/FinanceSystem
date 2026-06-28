package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.entities.Account;
import dev.samuel.financesystem.core.entities.Transaction;
import dev.samuel.financesystem.core.gateway.AccountGateway;
import dev.samuel.financesystem.infrastructure.mapper.AccountMapper;
import dev.samuel.financesystem.infrastructure.mapper.TransactionMapper;
import dev.samuel.financesystem.infrastructure.repository.AccountRepository;
import dev.samuel.financesystem.infrastructure.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Cacheable(value = "balance", key = "#userId")
    @Override
    public Account getBalance(Long userId) {
        return accountRepository.findByUserId(userId)
                .map(accountMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
