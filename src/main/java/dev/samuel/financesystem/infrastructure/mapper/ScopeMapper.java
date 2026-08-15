package dev.samuel.financesystem.infrastructure.mapper;

import dev.samuel.financesystem.infrastructure.persistence.Scope;
import dev.samuel.financesystem.infrastructure.persistence.Transaction;
import dev.samuel.financesystem.infrastructure.request.TransactionRequest;
import dev.samuel.financesystem.infrastructure.response.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScopeMapper {

    // Core → Persistence
    Scope toPersistenceEntity(dev.samuel.financesystem.core.entities.Scope scope);

    // Persistence → Core
    dev.samuel.financesystem.core.entities.Scope toDomain(Scope scope);

}
