package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.entities.Transaction;
import dev.samuel.financesystem.core.enums.Status;
import dev.samuel.financesystem.core.gateway.TransactionGateway;
import dev.samuel.financesystem.infrastructure.mapper.TransactionMapper;
import dev.samuel.financesystem.infrastructure.persistence.Account;
import dev.samuel.financesystem.infrastructure.repository.AccountRepository;
import dev.samuel.financesystem.infrastructure.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionGatewayImpl implements TransactionGateway {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;


    @Override
    @Transactional
    public Transaction transfer(Transaction transaction) {

        // Busca as contas
        Account origin = accountRepository.findById(transaction.originId())
                .orElseThrow(() -> new RuntimeException("Origin account not found"));

        Account destination = accountRepository.findById(transaction.destinationId())
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        // Valida saldo
        if (origin.getBalance().compareTo(transaction.amount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Debita e credita
        origin.setBalance(origin.getBalance().subtract(transaction.amount()));
        destination.setBalance(destination.getBalance().add(transaction.amount()));

        // Salva as contas
        accountRepository.save(origin);
        accountRepository.save(destination);

        // Salva a transação
        dev.samuel.financesystem.infrastructure.persistence.Transaction persistenceTransaction =
                transactionMapper.toPersistenceEntity(transaction);
        persistenceTransaction.setStatus(Status.COMPLETED);

        dev.samuel.financesystem.infrastructure.persistence.Transaction saved =
                transactionRepository.save(persistenceTransaction);

        return transactionMapper.toDomain(saved);
    }
}

