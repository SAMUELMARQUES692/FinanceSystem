package dev.samuel.financesystem.infrastructure.repository;

import dev.samuel.financesystem.infrastructure.persistence.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUserId(Long userId);

    @Query("SELECT a FROM Account a JOIN FETCH a.user WHERE a.user.id = :userId")
    Optional<Account> findByUserIdWithUser(@Param("userId") Long userId);

    @Query("SELECT a FROM Account a JOIN FETCH a.user WHERE a.pix = :pix")
    Optional<Account> findByPix(@Param("pix") String pix);

}
