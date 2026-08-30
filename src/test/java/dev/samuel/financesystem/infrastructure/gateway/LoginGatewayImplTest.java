package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.usecases.login.LoginInput;
import dev.samuel.financesystem.core.usecases.login.LoginOutput;
import dev.samuel.financesystem.infrastructure.configuration.TokenService;
import dev.samuel.financesystem.infrastructure.persistence.Scope;
import dev.samuel.financesystem.infrastructure.persistence.User;
import dev.samuel.financesystem.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoginGatewayImplTest {

    @InjectMocks
    LoginGatewayImpl loginGateway;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    TokenService tokenService;

    @Test
    void login() {
        dev.samuel.financesystem.infrastructure.persistence.Scope scope = Scope.builder()
                .id(1L)
                .name("ADMIN")
                .build();

        dev.samuel.financesystem.infrastructure.persistence.User user = User.builder()
                .id(1L)
                .name("Name Test")
                .email("emailTest@gmail.com")
                .password(passwordEncoder.encode("123899"))
                .createdAt(LocalDateTime.now())
                .scopes(List.of(scope))
                .build();

        LoginInput loginInput = LoginInput.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();

        Mockito.when(userRepository.findByEmail(loginInput.email())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(loginInput.password(), user.getPassword())).thenReturn(true);
        Mockito.when(tokenService.gerarToken(user)).thenReturn("token");

        loginGateway.login(loginInput);

        Mockito.verify(userRepository).findByEmail(loginInput.email());
        Mockito.verify(tokenService).gerarToken(user);
    }
}