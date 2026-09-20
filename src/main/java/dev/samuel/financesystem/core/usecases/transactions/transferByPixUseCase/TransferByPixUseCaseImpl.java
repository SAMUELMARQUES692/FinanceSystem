package dev.samuel.financesystem.core.usecases.transactions.transferByPixUseCase;

import dev.samuel.financesystem.core.entities.Transaction;
import dev.samuel.financesystem.core.gateway.TransactionGateway;

public class TransferByPixUseCaseImpl implements TransferByPixUseCase {

    private final TransactionGateway transactionGateway;


    public TransferByPixUseCaseImpl(TransactionGateway transactionGateway) {
        this.transactionGateway = transactionGateway;
    }

    @Override
    public Transaction execute(Transaction transaction) {
        return transactionGateway.transfer(transaction);
    }
}
