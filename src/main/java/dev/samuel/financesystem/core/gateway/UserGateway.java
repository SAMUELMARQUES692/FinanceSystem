package dev.samuel.financesystem.core.gateway;

import dev.samuel.financesystem.core.entities.User;

public interface UserGateway {

    User createUser(User user);
}
