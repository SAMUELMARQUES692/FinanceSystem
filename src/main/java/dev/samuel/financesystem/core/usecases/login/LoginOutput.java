package dev.samuel.financesystem.core.usecases.login;

import lombok.Builder;

@Builder
public record LoginOutput(
        String accessToken,
        String tokenType,
        Long expiresIn
) {
}
