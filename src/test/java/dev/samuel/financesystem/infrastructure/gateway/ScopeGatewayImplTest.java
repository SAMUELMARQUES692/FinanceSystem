package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.entities.Scope;
import dev.samuel.financesystem.infrastructure.mapper.ScopeMapper;
import dev.samuel.financesystem.infrastructure.repository.ScopeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ScopeGatewayImplTest {

    @InjectMocks
    ScopeGatewayImpl scopeGateway;

    @Mock
    ScopeRepository scopeRepository;

    @Mock
    ScopeMapper scopeMapper;

    @Test
    void findByName() {
        Scope scopeCore = Scope.builder()
                .id(1L)
                .name("ADMIN")
                .build();

        dev.samuel.financesystem.infrastructure.persistence.Scope scopeInfra = dev.samuel.financesystem.infrastructure.persistence.Scope.builder()
                .id(scopeCore.id())
                .name(scopeCore.name())
                .build();

        Mockito.when(scopeRepository.findByName(scopeInfra.getName())).thenReturn(Optional.of(scopeInfra));
        Mockito.when(scopeMapper.toDomain(scopeInfra)).thenReturn(scopeCore);

        scopeGateway.findByName(scopeInfra.getName());

        Mockito.verify(scopeRepository).findByName(scopeInfra.getName());
        Mockito.verify(scopeMapper).toDomain(scopeInfra);
    }
}