package dev.samuel.financesystem.core.usecases.transferUse;

import dev.samuel.financesystem.core.entities.Transaction;
import dev.samuel.financesystem.core.gateway.TransactionGateway;

public class TransferUseCaseImpl implements TransferUseCase {

    private final TransactionGateway transactionGateway;


    public TransferUseCaseImpl(TransactionGateway transactionGateway) {
        this.transactionGateway = transactionGateway;
    }

    @Override
    public Transaction execute(Transaction transaction) {
        return transactionGateway.transfer(transaction);
    }
}
