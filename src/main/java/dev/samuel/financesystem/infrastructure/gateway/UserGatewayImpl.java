package dev.samuel.financesystem.infrastructure.gateway;

import dev.samuel.financesystem.core.entities.Scope;
import dev.samuel.financesystem.core.entities.User;
import dev.samuel.financesystem.core.gateway.UserGateway;
import dev.samuel.financesystem.infrastructure.mapper.ScopeMapper;
import dev.samuel.financesystem.infrastructure.mapper.UserMapper;
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
    private final ScopeGatewayImpl scopeGateway;
    private final ScopeMapper scopeMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(User user) {

        if (userRepository.existsByEmail(user.email())) {
            throw new RuntimeException("Email " + user.email() + " já esta em uso");
        }

        Scope scopeUser = scopeGateway.findByName("USER");

        dev.samuel.financesystem.infrastructure.persistence.Scope convertScope = scopeMapper.toPersistenceEntity(scopeUser);
        dev.samuel.financesystem.infrastructure.persistence.User persistenceUser = userMapper.toPersistenceEntity(user);
        persistenceUser.setScopes(List.of(convertScope));
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

    @Override
    public User findByEmail(String email) {

        dev.samuel.financesystem.infrastructure.persistence.User usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return userMapper.toDomain(usuario);
    }
}
