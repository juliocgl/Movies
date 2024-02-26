package com.example.movies.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service which loads hard coded user-specific data.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JwtUserDetailsService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("user1".equals(username)) {
            return new User("user1", bCryptPasswordEncoder.encode("password"),
                    new ArrayList<>());
        } else if ("user2".equals(username)) {
            return new User("user2", bCryptPasswordEncoder.encode("password"),
                    new ArrayList<>());
        } else if ("user3".equals(username)) {
            return new User("user3", bCryptPasswordEncoder.encode("password"),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
