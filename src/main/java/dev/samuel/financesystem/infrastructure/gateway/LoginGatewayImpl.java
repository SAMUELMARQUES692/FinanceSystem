package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.gateway.LoginGateway;
import dev.samuel.financesystem.core.usecases.login.LoginInput;
import dev.samuel.financesystem.core.usecases.login.LoginOutput;
import dev.samuel.financesystem.infrastructure.configuration.TokenService;
import dev.samuel.financesystem.infrastructure.exception.UserOrPasswordIncorectException;
import dev.samuel.financesystem.infrastructure.persistence.Scope;
import dev.samuel.financesystem.infrastructure.persistence.User;
import dev.samuel.financesystem.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginGatewayImpl implements LoginGateway {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    private static final long EXPIRES_IN_SECONDS = 3600L;

    @Override
    public LoginOutput login(LoginInput loginInput) {

            User optUser = userRepository.findByEmail(loginInput.email())
                .filter(user -> passwordEncoder.matches(loginInput.password(), user.getPassword()))
                .orElseThrow(() -> new UserOrPasswordIncorectException(loginInput.email(), loginInput.password()));

            String token = tokenService.gerarToken(optUser);

            return LoginOutput.builder()
                    .accessToken(token)
                    .tokenType("Bearer")
                    .expiresIn(EXPIRES_IN_SECONDS)
                    .build();
        }

    private boolean isPasswordCorrect(String password, String savePassword) {
        return passwordEncoder.matches(password, savePassword);
    }

}
