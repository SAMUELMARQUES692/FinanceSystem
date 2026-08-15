package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.entities.Scope;
import dev.samuel.financesystem.core.gateway.ScopeGateway;
import dev.samuel.financesystem.infrastructure.mapper.ScopeMapper;
import dev.samuel.financesystem.infrastructure.repository.ScopeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScopeGatewayImpl implements ScopeGateway {

    private final ScopeMapper scopeMapper;
    private final ScopeRepository scopeRepository;

    @Override
    public Scope findByName(String name) {
       dev.samuel.financesystem.infrastructure.persistence.Scope scope =  scopeRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Scope não encontrado"));
        return scopeMapper.toDomain(scope);
    }
}
