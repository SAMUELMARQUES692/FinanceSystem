package dev.samuel.financesystem.infrastructure.mapper;

import dev.samuel.financesystem.infrastructure.persistence.Transaction;
import dev.samuel.financesystem.infrastructure.request.TransactionRequest;
import dev.samuel.financesystem.infrastructure.response.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    // Core → Persistence
    Transaction toPersistenceEntity(dev.samuel.financesystem.core.entities.Transaction transaction);

    // Persistence → Core
    dev.samuel.financesystem.core.entities.Transaction toDomain(Transaction transaction);

    // Request → Core
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "originId", ignore = true)
    @Mapping(target = "destinationId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    dev.samuel.financesystem.core.entities.Transaction toEntity(TransactionRequest request);

    // Core → Response
    TransactionResponse toTransactionResponse(dev.samuel.financesystem.core.entities.Transaction transaction);

}
