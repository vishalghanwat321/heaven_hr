package com.web.app.heaven_hr.config.security;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class WebSecurityConfigTest {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        UserDetails userDetails = new User("heavenhr", "password", Arrays.asList(grantedAuthority));
        return new InMemoryUserDetailsManager(userDetails);
    }
}