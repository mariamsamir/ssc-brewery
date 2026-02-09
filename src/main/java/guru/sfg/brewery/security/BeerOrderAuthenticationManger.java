package guru.sfg.brewery.security;

import guru.sfg.brewery.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class BeerOrderAuthenticationManger {

    public boolean isCustomerMatches(Authentication authentication, UUID customerId) {
        User user = (User) authentication.getPrincipal();
        log.debug("Checking if user matches for customer id {}", customerId);

        return user.getCustomer().getId().equals(customerId);
    }

}
