package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.entities.Scope;
import dev.samuel.financesystem.infrastructure.mapper.ScopeMapper;
import dev.samuel.financesystem.infrastructure.mapper.UserMapper;
import dev.samuel.financesystem.infrastructure.persistence.User;
import dev.samuel.financesystem.infrastructure.repository.ScopeRepository;
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

@ExtendWith(MockitoExtension.class)
class UserGatewayImplTest {

    @InjectMocks
    UserGatewayImpl userGateway;

    @Mock
    UserRepository userRepository;

    @Mock
    ScopeGatewayImpl scopeGateway;

    @Mock
    ScopeMapper scopeMapper;

    @Mock
    UserMapper userMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void createUser() {
        Scope scopeCore = Scope.builder()
                .id(1L)
                .name("USER")
                .build();

        dev.samuel.financesystem.infrastructure.persistence.Scope scopeInfra = dev.samuel.financesystem.infrastructure.persistence.Scope.builder()
                .id(1L)
                .name("USER")
                .build();

        User userInfra = User.builder()
                .id(1L)
                .name("Name Test")
                .email("emailtest@gmail.com")
                .password("Senha Teste")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.core.entities.User userCore = dev.samuel.financesystem.core.entities.User.builder()
                .id(1L)
                .name("Name Test")
                .email("emailtest@gmail.com")
                .password("Senha Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(userRepository.existsByEmail(userCore.email())).thenReturn(false);
        Mockito.when(scopeGateway.findByName(scopeCore.name())).thenReturn(scopeCore);
        Mockito.when(scopeMapper.toPersistenceEntity(scopeCore)).thenReturn(scopeInfra);
        Mockito.when(userMapper.toPersistenceEntity(userCore)).thenReturn(userInfra);
        Mockito.when(passwordEncoder.encode(userCore.password())).thenReturn("Senha");
        Mockito.when(userRepository.save(userInfra)).thenReturn(userInfra);
        Mockito.when(userMapper.toDomain(userInfra)).thenReturn(userCore);

        userGateway.createUser(userCore);

        Mockito.verify(userRepository).existsByEmail(userCore.email());
        Mockito.verify(scopeGateway).findByName(scopeCore.name());
        Mockito.verify(userMapper).toPersistenceEntity(userCore);
        Mockito.verify(passwordEncoder).encode(userCore.password());
        Mockito.verify(userRepository).save(userInfra);
        Mockito.verify(userMapper).toDomain(userInfra);
    }

    @Test
    void updateUser() {
        User userInfra = User.builder()
                .id(1L)
                .name("Name Test")
                .email("emailtest@gmail.com")
                .password("Senha Teste")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.core.entities.User userCore = dev.samuel.financesystem.core.entities.User.builder()
                .id(1L)
                .name("Name Test")
                .email("emailtest@gmail.com")
                .password("Senha Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(userRepository.findById(userInfra.getId())).thenReturn(Optional.of(userInfra));
        Mockito.when(userMapper.toPersistenceEntity(userCore)).thenReturn(userInfra);
        Mockito.when(passwordEncoder.encode(userCore.password())).thenReturn("Password");
        Mockito.when(userRepository.save(userInfra)).thenReturn(userInfra);
        Mockito.when(userMapper.toDomain(userInfra)).thenReturn(userCore);

        userGateway.updateUser(userInfra.getId(), userCore);

        Mockito.verify(userRepository).findById(userInfra.getId());
        Mockito.verify(userMapper).toPersistenceEntity(userCore);
        Mockito.verify(passwordEncoder).encode(userCore.password());
        Mockito.verify(userRepository).save(userInfra);
        Mockito.verify(userMapper).toDomain(userInfra);
    }

    @Test
    void deleteUser() {
        User userInfra = User.builder()
                .id(1L)
                .name("Name Test")
                .email("emailtest@gmail.com")
                .password("Senha Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(userRepository.findById(userInfra.getId())).thenReturn(Optional.of(userInfra));

        userGateway.deleteUser(userInfra.getId());

        Mockito.verify(userRepository).findById(userInfra.getId());
        Mockito.verify(userRepository).deleteById(userInfra.getId());
    }

    @Test
    void findByEmail() {
        User userInfra = User.builder()
                .id(1L)
                .name("Name Test")
                .email("emailtest@gmail.com")
                .password("Senha Teste")
                .createdAt(LocalDateTime.now())
                .build();

        dev.samuel.financesystem.core.entities.User userCore = dev.samuel.financesystem.core.entities.User.builder()
                .id(1L)
                .name("Name Test")
                .email("emailtest@gmail.com")
                .password("Senha Teste")
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(userRepository.findByEmail(userInfra.getEmail())).thenReturn(Optional.of(userInfra));
        Mockito.when(userMapper.toDomain(userInfra)).thenReturn(userCore);

        userGateway.findByEmail(userInfra.getEmail());

        Mockito.verify(userRepository).findByEmail(userInfra.getEmail());
        Mockito.verify(userMapper).toDomain(userInfra);
    }
}