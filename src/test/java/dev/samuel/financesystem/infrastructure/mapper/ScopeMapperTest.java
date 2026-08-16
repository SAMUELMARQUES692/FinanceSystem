package dev.samuel.financesystem.infrastructure.mapper;

import dev.samuel.financesystem.core.entities.Scope;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ScopeMapperTest {

    private final ScopeMapper mapper = Mappers.getMapper(ScopeMapper.class);

    @Test
    void toPersistenceEntity() {
        Scope scopeCore = Scope.builder()
                .id(1L)
                .name("USER")
                .build();

        dev.samuel.financesystem.infrastructure.persistence.Scope scopeInfra = mapper.toPersistenceEntity(scopeCore);

        assertNotNull(scopeInfra);

        assertEquals(scopeCore.id(), scopeInfra.getId());
        assertEquals(scopeCore.name(), scopeInfra.getName());
    }

    @Test
    void toDomain() {
        dev.samuel.financesystem.infrastructure.persistence.Scope scopeInfra = dev.samuel.financesystem.infrastructure.persistence.Scope.builder()
                .id(1L)
                .name("USER")
                .build();

        Scope scopeCore = mapper.toDomain(scopeInfra);

        assertNotNull(scopeCore);

        assertEquals(scopeInfra.getId(), scopeCore.id());
        assertEquals(scopeInfra.getName(), scopeCore.name());
    }
}