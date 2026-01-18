package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                httpBasic(auth ->
                        auth.authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                ).
                authorizeRequests(authorize -> {
                            authorize.antMatchers("/", "/webjars/**", "/resources/**", "/login").permitAll()
                                    .antMatchers("/beers/**").permitAll()
                                    .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                                    .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
                        }
                ).
                authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();

    }


    // Fluent API

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}admin") // {noop} means no operation password encoder store as a plain text
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("{noop}user")
                .roles("USER")
                .and()
                .withUser("scott")
                .password("{noop}tiger")
                .roles("CUSTOMER");
    }

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
