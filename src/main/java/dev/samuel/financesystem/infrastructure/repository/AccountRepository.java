package dev.samuel.financesystem.infrastructure.repository;

import dev.samuel.financesystem.infrastructure.persistence.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUserId(Long userId);

}
