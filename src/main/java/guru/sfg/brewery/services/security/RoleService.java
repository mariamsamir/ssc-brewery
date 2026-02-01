package guru.sfg.brewery.services.security;

import guru.sfg.brewery.domain.security.Role;

public interface RoleService {

    Role addRole(Role role);

    Role updateRole(Role role);
}
