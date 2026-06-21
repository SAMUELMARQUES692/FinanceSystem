package dev.samuel.financesystem.infrastructure.mapper;

import dev.samuel.financesystem.infrastructure.request.UserRequest;
import dev.samuel.financesystem.infrastructure.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Core → Persistence
    dev.samuel.financesystem.infrastructure.persistence.User toPersistenceEntity(
            dev.samuel.financesystem.core.entities.User user);

    // Persistence → Core
    dev.samuel.financesystem.core.entities.User toDomain(
            dev.samuel.financesystem.infrastructure.persistence.User user);

    // Request → Core
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "scopes", source = "scopes", qualifiedByName = "mapScopeIdsToScopeEntities")
    dev.samuel.financesystem.core.entities.User toEntity(UserRequest request);

    // Core → Response
    @Mapping(target = "scopes", source = "scopes", qualifiedByName = "mapScopesToStrings")
    UserResponse toUserResponse(dev.samuel.financesystem.core.entities.User user);

    @Named("mapScopeIdsToScopeEntities")
    default List<dev.samuel.financesystem.core.entities.Scope> mapScopeIdsToScopeEntities(List<Long> scopeIds) {
        if (scopeIds == null) return List.of();
        return scopeIds.stream()
                .map(id -> new dev.samuel.financesystem.core.entities.Scope(id, null))
                .toList();
    }

    @Named("mapScopesToStrings")
    default List<String> mapScopesToStrings(List<dev.samuel.financesystem.core.entities.Scope> scopes) {
        if (scopes == null) return List.of();
        return scopes.stream()
                .map(dev.samuel.financesystem.core.entities.Scope::name)
                .toList();
    }
}