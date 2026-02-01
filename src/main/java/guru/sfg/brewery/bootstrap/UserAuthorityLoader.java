package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.services.security.AuthorityService;
import guru.sfg.brewery.services.security.RoleService;
import guru.sfg.brewery.services.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserAuthorityLoader implements CommandLineRunner {

    private final UserService userService;
    private final AuthorityService authorityService;
    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.countUsers() == 0) {
            log.info("Loading authorities");
            addUsers();
        }
        log.info("Users loaded {}", userService.countUsers());
    }

    private void addUsers() {
        Authority createBeer = authorityService.addAuthority("beer.create");
        Authority updateBeer = authorityService.addAuthority("beer.update");
        Authority readBeer = authorityService.addAuthority("beer.read");
        Authority deleteBeer = authorityService.addAuthority("beer.delete");


        Authority createCustomer = authorityService.addAuthority("customer.create");
        Authority updateCustomer = authorityService.addAuthority("customer.update");
        Authority readCustomer = authorityService.addAuthority("customer.read");
        Authority deleteCustomer = authorityService.addAuthority("customer.delete");

        Authority createBrewery = authorityService.addAuthority("brewery.create");
        Authority updateBrewery = authorityService.addAuthority("brewery.update");
        Authority readBrewery = authorityService.addAuthority("brewery.read");
        Authority deleteBrewery = authorityService.addAuthority("brewery.delete");


        Role adminRole = roleService.addRole(Role.builder().name("ADMIN").build());

        Role customerRole = roleService.addRole(Role.builder().name("CUSTOMER").build());

        Role userRole = roleService.addRole(Role.builder().name("USER").build());

        adminRole.setAuthorities(Set.of(createBeer, readBeer, updateBeer, deleteBeer,
                createCustomer, readCustomer, updateCustomer, deleteCustomer,
                createBrewery, readBrewery, updateBrewery, deleteBrewery));

        customerRole.setAuthorities(Set.of(readBeer, readCustomer, readBrewery));

        userRole.setAuthorities(Set.of(readBeer));

        roleService.updateRole(adminRole);
        roleService.updateRole(customerRole);
        roleService.updateRole(userRole);

        userService.addUser("admin", "admin", List.of(adminRole));
        userService.addUser("user", "user", List.of(userRole));
        userService.addUser("scott", "tiger", List.of(customerRole));


    }

}
