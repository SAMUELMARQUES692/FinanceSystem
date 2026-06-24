package dev.samuel.financesystem.core.gateway;

import dev.samuel.financesystem.core.entities.Transaction;

public interface TransactionGateway {

    Transaction transfer(Transaction transaction);
}
