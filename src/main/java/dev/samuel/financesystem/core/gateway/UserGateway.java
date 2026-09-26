package dev.samuel.financesystem.core.gateway;

import dev.samuel.financesystem.core.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserGateway {

    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User findByEmail(String email);

    List<User> findAllUsers();

    User findUserById(Long id);
}
