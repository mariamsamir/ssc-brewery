package guru.sfg.brewery.services.security;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.repositories.security.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImp implements AuthorityService {

    @Autowired
    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImp(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority addAuthority(String permission) {
        Authority authority = Authority.builder().permission(permission).build();
        authorityRepository.save(authority);
        return authority;
    }
}
