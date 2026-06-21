package dev.samuel.financesystem.core.entities;

import java.time.LocalDateTime;
import java.util.List;

public record User(

        Long id,
        String name,
        String email,
        String password,
        LocalDateTime createdAt,
        List<Scope> scopes
) {
}
