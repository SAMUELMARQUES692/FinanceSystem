package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.entities.Transaction;
import dev.samuel.financesystem.core.enums.Status;
import dev.samuel.financesystem.core.gateway.TransactionGateway;
import dev.samuel.financesystem.infrastructure.exception.DestinationAccountNotFoundException;
import dev.samuel.financesystem.infrastructure.exception.InsufficientBalanceException;
import dev.samuel.financesystem.infrastructure.exception.OriginAccountNotFoundException;
import dev.samuel.financesystem.infrastructure.exception.SameAccountException;
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
                .orElseThrow(() -> new OriginAccountNotFoundException("Origin account not found"));

        Account destination = accountRepository.findById(transaction.destinationId())
                .orElseThrow(() -> new DestinationAccountNotFoundException("Destination account not found"));

        // Valida se não é a mesma conta <- veio antes do saldo
        if (origin.getUserId().equals(destination.getUserId())) {
            throw new SameAccountException("Origin account can't be equals destination account");
        }

        // Valida saldo
        if (origin.getBalance().compareTo(transaction.amount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
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

