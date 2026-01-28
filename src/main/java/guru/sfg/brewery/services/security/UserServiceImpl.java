package guru.sfg.brewery.services.security;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import guru.sfg.brewery.repositories.security.UserRepository;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;


    @Override
    public void addUser(String userName, String password, List<String> roles) {

        List<Authority> authorities = roles.stream()
                .map(role -> authorityRepository.findByRole(role).
                        orElseThrow())
                .collect(Collectors.toList());

        User user = User.builder()
                .username(userName)
                .password(SfgPasswordEncoderFactories.createDelegatingPasswordEncoder().encode(password))
                .authorities(authorities)
                .build();
        userRepository.save(user);
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
