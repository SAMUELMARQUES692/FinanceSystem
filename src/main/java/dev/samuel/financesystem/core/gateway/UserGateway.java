package dev.samuel.financesystem.core.gateway;

import dev.samuel.financesystem.core.entities.User;

import java.util.List;

public interface UserGateway {

    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User findByEmail(String email);

    List<User> findAllUsers();
}
