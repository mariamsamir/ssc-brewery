package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.Role;
import guru.sfg.brewery.services.security.AuthorityService;
import guru.sfg.brewery.services.security.RoleService;
import guru.sfg.brewery.services.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserAuthorityLoader {

    private final UserService userService;
    private final AuthorityService authorityService;
    private final RoleService roleService;


    public void addUsers() {
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

        Authority createOrder = authorityService.addAuthority("order.create");
        Authority updateOrder = authorityService.addAuthority("order.update");
        Authority readOrder = authorityService.addAuthority("order.read");
        Authority deleteOrder = authorityService.addAuthority("order.delete");
        Authority pickupOrder = authorityService.addAuthority("order.pickup");

        Authority createOrderCustomer = authorityService.addAuthority("order.customer.create");
        Authority updateOrderCustomer = authorityService.addAuthority("order.customer.update");
        Authority readOrderCustomer= authorityService.addAuthority("order.customer.read");
        Authority deleteOrderCustomer = authorityService.addAuthority("order.customer.delete");
        Authority pickupOrderCustomer = authorityService.addAuthority("order.customer.pickup");

        Role adminRole = roleService.addRole(Role.builder().name("ADMIN").build());

        Role customerRole = roleService.addRole(Role.builder().name("CUSTOMER").build());

        Role userRole = roleService.addRole(Role.builder().name("USER").build());

        adminRole.setAuthorities(Set.of(createBeer, readBeer, updateBeer, deleteBeer,
                createCustomer, readCustomer, updateCustomer, deleteCustomer,
                createBrewery, readBrewery, updateBrewery, deleteBrewery,
                createOrder, updateOrder, readOrder, deleteOrder, pickupOrder));

        customerRole.setAuthorities(Set.of(readBeer, readCustomer, readBrewery,
                createOrderCustomer, updateOrderCustomer, deleteOrderCustomer, readOrderCustomer, pickupOrderCustomer));

        userRole.setAuthorities(Set.of(readBeer));

        roleService.updateRole(adminRole);
        roleService.updateRole(customerRole);
        roleService.updateRole(userRole);

        userService.addUser("admin", "admin", List.of(adminRole));
        userService.addUser("user", "user", List.of(userRole));
        userService.addUser("scott", "tiger", List.of(customerRole));


    }

}
