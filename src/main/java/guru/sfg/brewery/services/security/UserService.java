package guru.sfg.brewery.services.security;

import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.domain.security.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User addUser(String userName, String password, List<Role> roles);

    Optional<User> findByUsername(String username);

    long countUsers();
}
