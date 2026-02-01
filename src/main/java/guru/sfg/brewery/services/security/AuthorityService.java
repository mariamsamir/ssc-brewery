package guru.sfg.brewery.services.security;

import guru.sfg.brewery.domain.security.Authority;

public interface AuthorityService {

    Authority addAuthority(String permission);
}
