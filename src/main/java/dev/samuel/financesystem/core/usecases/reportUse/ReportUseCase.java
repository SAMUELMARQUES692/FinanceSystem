package dev.samuel.financesystem.core.usecases.reportUse;

import dev.samuel.financesystem.core.entities.Transaction;

import java.util.List;

public interface ReportUseCase {

    List<Transaction> execute(Long accountId);
}
