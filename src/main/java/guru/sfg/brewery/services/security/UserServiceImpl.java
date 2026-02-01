package guru.sfg.brewery.services.security;

import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.RoleRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    private final RoleRepository authorityRepository;


    @Override
    public User addUser(String userName, String password, List<Role> roles) {


        User user = User.builder()
                .username(userName)
                .password(SfgPasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password))
                .roles(roles)
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public long countUsers() {
        return userRepository.count();
    }
}
