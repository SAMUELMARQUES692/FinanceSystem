package dev.samuel.financesystem.infrastructure.configuration;

import dev.samuel.financesystem.infrastructure.persistence.Scope;
import dev.samuel.financesystem.infrastructure.persistence.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;

    public String gerarToken(User user) {
        Instant agora = Instant.now();

        String scopesStr = user.getScopes().stream()
                .map(Scope::getName)
                .collect(Collectors.joining(" ")); // padrão OAuth2: scopes separados por espaço

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("finance-system")
                .issuedAt(agora)
                .expiresAt(agora.plus(1, ChronoUnit.HOURS))
                .subject(user.getEmail())
                .claim("scope", scopesStr)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(SignatureAlgorithm.RS256).build(),
                claims
        )).getTokenValue();
    }

}
