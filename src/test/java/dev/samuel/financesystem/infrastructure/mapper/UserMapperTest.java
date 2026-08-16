package dev.samuel.financesystem.infrastructure.mapper;

import dev.samuel.financesystem.core.entities.Scope;
import dev.samuel.financesystem.core.entities.User;
import dev.samuel.financesystem.infrastructure.request.UserRequest;
import dev.samuel.financesystem.infrastructure.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toPersistenceEntity() {
        User userCore = User.builder()
                .id(1L)
                .name("Nome Teste")
                .email("Email Teste")
                .password("Senha Teste")
                .createdAt(LocalDateTime.now())
                .scopes(List.of(Scope.builder().id(1L).name("USER").build()))
                .build();

        dev.samuel.financesystem.infrastructure.persistence.User userInfra = mapper.toPersistenceEntity(userCore);

        assertNotNull(userInfra);

        assertEquals(userCore.id(), userInfra.getId());
        assertEquals(userCore.name(), userInfra.getName());
        assertEquals(userCore.email(), userInfra.getEmail());
        assertEquals(userCore.password(), userInfra.getPassword());
        assertEquals(userCore.createdAt(), userInfra.getCreatedAt());
    }

    @Test
    void toDomain() {
        dev.samuel.financesystem.infrastructure.persistence.User userInfra = dev.samuel.financesystem.infrastructure.persistence.User.builder()
                .id(1L)
                .name("Nome Teste")
                .email("Email Teste")
                .password("Senha Teste")
                .createdAt(LocalDateTime.now())
                .scopes(List.of(dev.samuel.financesystem.infrastructure.persistence.Scope.builder().id(1L).name("USER").build()))
                .build();

        User userCore = mapper.toDomain(userInfra);

        assertNotNull(userCore);

        assertEquals(userInfra.getId(), userCore.id());
        assertEquals(userInfra.getName(), userCore.name());
        assertEquals(userInfra.getEmail(), userCore.email());
        assertEquals(userInfra.getPassword(), userCore.password());
        assertEquals(userInfra.getCreatedAt(), userCore.createdAt());
    }

    @Test
    void toEntity() {
        Scope scopeCore = Scope.builder()
                .id(1L)
                .name("USER")
                .build();

        UserRequest request = UserRequest.builder()
                .name("Nome Teste")
                .email("Email Teste")
                .password("Senha Teste")
                .scopes(List.of(scopeCore.id()))
                .build();

        User userCore = mapper.toEntity(request);

        assertNotNull(userCore);

        assertEquals(request.name(), userCore.name());
        assertEquals(request.email(), userCore.email());
        assertEquals(request.password(), userCore.password());
    }

    @Test
    void toUserResponse() {
        User userCore = User.builder()
                .id(1L)
                .name("Nome Teste")
                .email("Email Teste")
                .password("Senha Teste")
                .createdAt(LocalDateTime.now())
                .scopes(List.of(Scope.builder().id(1L).name("USER").build()))
                .build();

        UserResponse response = mapper.toUserResponse(userCore);

        assertNotNull(response);

        assertEquals(userCore.id(), response.id());
        assertEquals(userCore.name(), response.name());
        assertEquals(userCore.email(), response.email());
        assertEquals(userCore.createdAt(), response.createdAt());
    }

    @Test
    void mapScopeIdsToScopeEntities() {
        Long scopeId1 = 1L;
        Long scopeId2 = 2L;

        List<Long> scopes = List.of(scopeId1, scopeId2);

        List<Scope> resultado = mapper.mapScopeIdsToScopeEntities(scopes);


        assertEquals(2, resultado.size());
        assertNotNull(resultado);
    }

    @Test
    void mapScopesToStrings() {
        Scope scope1 = Scope.builder().id(1L).name("ADMIN").build();
        Scope scope2 = Scope.builder().id(2L).name("USER").build();

        List<Scope> scopes = List.of(scope1, scope2);

        List<String> resultado = mapper.mapScopesToStrings(scopes);

        // Assert - confirma que os nomes foram extraídos corretamente
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains("ADMIN"));
        assertTrue(resultado.contains("USER"));
    }
}