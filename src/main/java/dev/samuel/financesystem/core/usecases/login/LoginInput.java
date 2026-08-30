package dev.samuel.financesystem.core.usecases.login;

import lombok.Builder;

@Builder
public record LoginInput(
        String email,
        String password
) {
}
