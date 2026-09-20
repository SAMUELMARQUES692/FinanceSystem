package dev.samuel.financesystem.infrastructure.repository;

import dev.samuel.financesystem.infrastructure.persistence.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t JOIN FETCH t.origin o JOIN FETCH t.destination d WHERE o.id = :originId OR d.id = :destinationId")
    List<Transaction> findByOriginIdOrDestinationId(Long originId, Long destinationId);
}
