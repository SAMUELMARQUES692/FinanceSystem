package dev.samuel.financesystem.core.gateway;

import dev.samuel.financesystem.core.entities.Transaction;

import java.util.List;

public interface TransactionGateway {

    Transaction transfer(Transaction transaction);

    List<Transaction> findByAccountId(Long accountId);
}
