package dev.samuel.financesystem.core.usecases.login;

public record LoginInput(
        String email,
        String password
) {
}
