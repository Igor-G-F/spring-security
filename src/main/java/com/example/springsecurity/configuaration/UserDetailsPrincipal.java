package com.example.springsecurity.configuaration;

import com.example.springsecurity.entity.User;
import com.example.springsecurity.repos.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsPrincipal implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsPrincipal(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s).orElseThrow(
                () -> new UsernameNotFoundException("Could not find user with username: " + s));

        return new UserPrincipal(user);
    }

}
