package dev.samuel.financesystem.core.usecases.scope.findScope;

import dev.samuel.financesystem.core.entities.Scope;

public interface FindScopeByNameUseCase {

    Scope execute(String name);
}
