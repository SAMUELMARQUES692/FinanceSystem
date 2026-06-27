package dev.samuel.financesystem.core.usecases.reportUse;

import dev.samuel.financesystem.core.entities.Transaction;
import dev.samuel.financesystem.core.gateway.TransactionGateway;

import java.util.List;

public class ReportUseCaseImpl implements ReportUseCase{

    private final TransactionGateway transactionGateway;

    public ReportUseCaseImpl(TransactionGateway transactionGateway) {
        this.transactionGateway = transactionGateway;
    }

    @Override
    public List<Transaction> execute(Long accountId) {
        return transactionGateway.findByAccountId(accountId);
    }
}
