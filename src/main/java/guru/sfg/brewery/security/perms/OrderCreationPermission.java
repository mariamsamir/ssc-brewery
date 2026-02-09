package guru.sfg.brewery.security.perms;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('order.create') " +
        " OR hasAuthority('order.customer.create') " +
        " AND @beerOrderAuthenticationManger.isCustomerMatches(authentication, #customerId)")
public @interface OrderCreationPermission {
}
