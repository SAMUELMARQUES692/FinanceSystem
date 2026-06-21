package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.gateway.LoginGateway;
import dev.samuel.financesystem.core.usecases.login.LoginInput;
import dev.samuel.financesystem.core.usecases.login.LoginOutput;
import dev.samuel.financesystem.infrastructure.exception.UserOrPasswordIncorectException;
import dev.samuel.financesystem.infrastructure.persistence.Scope;
import dev.samuel.financesystem.infrastructure.persistence.User;
import dev.samuel.financesystem.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@Service
@RequiredArgsConstructor
public class LoginGatewayImpl implements LoginGateway {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    @Override
    public LoginOutput login(LoginInput loginInput) {

        Optional<User> optUser = userRepository.findByEmail(loginInput.email());
        if (optUser.isEmpty() || !isPasswordCorrect(loginInput.password(), optUser.get().getPassword())) {
            throw new UserOrPasswordIncorectException(loginInput.password(), loginInput.email());
        }

        User savedUser= optUser.get();
        List<String> scopes = savedUser.getScopes().stream()
                .map(Scope::getName)
                .toList();
        long expiresIn = 600L;

        JwtClaimsSet jwt = JwtClaimsSet.builder()
                .issuer("financesystem-api")
                .subject(savedUser.getName())
                .expiresAt(Instant.now().plusSeconds(expiresIn))
                .issuedAt(Instant.now())
                .claim("email", savedUser.getEmail())
                .claim("scope", String.join(" ", scopes))
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(jwt)).getTokenValue();

        return LoginOutput.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .build();

    }

    private boolean isPasswordCorrect(String password, String savePassword) {
        return passwordEncoder.matches(password, savePassword);
    }

}
