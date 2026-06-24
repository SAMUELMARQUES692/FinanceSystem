package dev.samuel.financesystem.core.usecases.transferUse;

import dev.samuel.financesystem.core.entities.Transaction;

public interface TransferUseCase {

    Transaction execute(Transaction transaction);
}
