package guru.sfg.brewery.services.security;

import guru.sfg.brewery.domain.security.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void addUser(String userName, String password, List<String> roles);

    Optional<User> findByUsername(String username);

    long countUsers();
}
