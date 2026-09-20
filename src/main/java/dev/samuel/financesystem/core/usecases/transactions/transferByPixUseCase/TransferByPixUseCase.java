package dev.samuel.financesystem.core.usecases.transactions.transferByPixUseCase;

import dev.samuel.financesystem.core.entities.Transaction;

public interface TransferByPixUseCase {

    Transaction execute(Transaction transaction);
}
