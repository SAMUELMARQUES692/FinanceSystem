package dev.samuel.financesystem.core.usecases.findScope;

import dev.samuel.financesystem.core.entities.Scope;

public interface FindScopeByNameUseCase {

    Scope execute(String name);
}
