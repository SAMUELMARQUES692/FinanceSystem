package dev.samuel.financesystem.infrastructure.repository;

import dev.samuel.financesystem.infrastructure.persistence.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByOriginIdOrDestinationId(Long originId, Long destinationId);
}
