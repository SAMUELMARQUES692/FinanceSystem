package dev.samuel.financesystem.infrastructure.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken,
        String tokenType,
        Long expiresIn
) {
}
