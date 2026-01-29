package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.services.security.AuthorityService;
import guru.sfg.brewery.services.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserAuthorityLoader implements CommandLineRunner {

    private final UserService userService;
    private final AuthorityService authorityService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.countUsers() == 0) {
            log.info("Loading authorities");
            addAuthority();

            addUsers();
        }
        log.info("Users loaded {}",  userService.countUsers());
    }

    private void addUsers() {
        userService.addUser("admin", "admin", List.of("ROLE_ADMIN"));
        userService.addUser("user", "user", List.of("ROLE_USER"));
        userService.addUser("scott", "tiger", List.of("ROLE_CUSTOMER"));

    }

    private void addAuthority() {
        authorityService.addAuthority("ROLE_ADMIN");
        authorityService.addAuthority("ROLE_USER");
        authorityService.addAuthority("ROLE_CUSTOMER");
    }
}
