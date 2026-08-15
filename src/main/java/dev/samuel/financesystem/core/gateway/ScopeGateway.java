package dev.samuel.financesystem.core.gateway;

import dev.samuel.financesystem.core.entities.Scope;

public interface ScopeGateway {

    Scope findByName (String name);
}
