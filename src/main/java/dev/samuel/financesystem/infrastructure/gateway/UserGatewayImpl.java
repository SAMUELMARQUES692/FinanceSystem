package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.entities.User;
import dev.samuel.financesystem.core.gateway.UserGateway;
import dev.samuel.financesystem.infrastructure.mapper.UserMapper;
import dev.samuel.financesystem.infrastructure.repository.ScopeRepository;
import dev.samuel.financesystem.infrastructure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGatewayImpl implements UserGateway {

    private final UserRepository userRepository;
    private final ScopeRepository scopeRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(User user) {
        dev.samuel.financesystem.infrastructure.persistence.User persistenceUser = userMapper.toPersistenceEntity(user);

        // Busca os scopes no banco pelos ids
        if (user.scopes() != null && !user.scopes().isEmpty()) {
            List<Long> scopeIds = user.scopes().stream()
                    .map(scope -> scope.id())
                    .toList();

            List<dev.samuel.financesystem.infrastructure.persistence.Scope> scopes = scopeRepository.findAllById(scopeIds);
            persistenceUser.setScopes(scopes);
        }

        persistenceUser.setPassword(passwordEncoder.encode(user.password()));
        dev.samuel.financesystem.infrastructure.persistence.User salvo = userRepository.save(persistenceUser);
        return userMapper.toDomain(salvo);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) {
         userRepository.findById(id)
                 .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        dev.samuel.financesystem.infrastructure.persistence.User persistenceUser = userMapper.toPersistenceEntity(user);
        persistenceUser.setId(id);

        persistenceUser.setPassword(passwordEncoder.encode(user.password()));
        dev.samuel.financesystem.infrastructure.persistence.User saved = userRepository.save(persistenceUser);
        return userMapper.toDomain(saved);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        userRepository.deleteById(id);
    }
}
