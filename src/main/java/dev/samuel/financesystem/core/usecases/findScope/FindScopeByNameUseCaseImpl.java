package dev.samuel.financesystem.core.usecases.findScope;

import dev.samuel.financesystem.core.entities.Scope;
import dev.samuel.financesystem.core.gateway.ScopeGateway;

public class FindScopeByNameUseCaseImpl implements FindScopeByNameUseCase {

    private final ScopeGateway scopeGateway;

    public FindScopeByNameUseCaseImpl(ScopeGateway scopeGateway) {
        this.scopeGateway = scopeGateway;
    }

    @Override
    public Scope execute(String name) {
        return scopeGateway.findByName(name);
    }
}
