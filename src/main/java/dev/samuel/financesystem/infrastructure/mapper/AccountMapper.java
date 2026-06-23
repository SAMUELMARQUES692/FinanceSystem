package dev.samuel.financesystem.infrastructure.mapper;

import dev.samuel.financesystem.infrastructure.persistence.Account;
import dev.samuel.financesystem.infrastructure.request.AccountRequest;
import dev.samuel.financesystem.infrastructure.response.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    // Core → Persistence
    Account toPersistenceEntity(dev.samuel.financesystem.core.entities.Account account);

    // Persistence → Core
    dev.samuel.financesystem.core.entities.Account toDomain(Account account);

    // Request → Core
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true) // virá do token JWT
    @Mapping(target = "createdAt", ignore = true)
    dev.samuel.financesystem.core.entities.Account toEntity(AccountRequest request);

    // Core → Response
    AccountResponse toAccountResponse(dev.samuel.financesystem.core.entities.Account account);

}
