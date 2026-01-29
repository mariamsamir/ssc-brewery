package guru.sfg.brewery.config;

import guru.sfg.brewery.security.AuthenticationFilter;
import guru.sfg.brewery.security.RestAuthHeaderFilter;
import guru.sfg.brewery.security.RestAuthParameterFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    public AuthenticationFilter restAuthHeaderFilter(AuthenticationManager authenticationManager) {
        RestAuthHeaderFilter restAuthHeaderFilter =
                new RestAuthHeaderFilter(new AntPathRequestMatcher("/api/**"));
        restAuthHeaderFilter.setAuthenticationManager(authenticationManager);
        return restAuthHeaderFilter;
    }

    public AuthenticationFilter restAuthParamFilter(AuthenticationManager authenticationManager) {
        RestAuthParameterFilter restAuthParameterFilter =
                new RestAuthParameterFilter(new AntPathRequestMatcher("/api/**"));
        restAuthParameterFilter.setAuthenticationManager(authenticationManager);
        return restAuthParameterFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.addFilterBefore(restAuthHeaderFilter(authenticationManager()),
//                        UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(restAuthParamFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
//                .csrf().disable();
        http.
//                httpBasic(auth ->
//                        auth.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
//                ).
        authorizeRequests(authorize -> {
            authorize
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/", "/webjars/**", "/resources/**", "/login").permitAll()
                    .antMatchers("/beers/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/beer/**").hasAnyRole("USER", "ADMIN","CUSTOMER")
                    .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").hasAnyRole("USER", "ADMIN","CUSTOMER")
//                    .mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
                    .mvcMatchers(HttpMethod.GET, "/brewery/api/v1/breweries").hasAnyRole("ADMIN", "CUSTOMER")
                    .mvcMatchers("/brewery/breweries").hasAnyRole("ADMIN", "CUSTOMER");
        }
).
                authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin().and().httpBasic()
                .and().csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//        return new LdapShaPasswordEncoder();
//        return new BCryptPasswordEncoder();
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // Fluent API

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("{ldap}" + new LdapShaPasswordEncoder().encode("admin")) // {noop} means no operation password encoder store as a plain text
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password("{bcrypt}" + new BCryptPasswordEncoder().encode("user"))
//                .roles("USER")
//                .and()
//                .withUser("scott")
//                .password("{bcrypt15}" + new BCryptPasswordEncoder().encode("tiger"))
//                .roles("CUSTOMER");
//    }

//    @Override
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USER")
//                .build();
//    UserDetails scott = User.withDefaultPasswordEncoder()
//                .username("scott")
//                .password("tiger")
//                .roles("CUSTOMER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin,user,scott);
//    }
}
