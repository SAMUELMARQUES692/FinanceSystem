package dev.samuel.financesystem.infrastructure.repository;

import dev.samuel.financesystem.infrastructure.persistence.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScopeRepository extends JpaRepository<Scope, Long> {

    Optional<Scope> findByName(String name);
}
