package com.ecommerce.backend.config.security;

import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.storage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isEmpty()){
                throw new UsernameNotFoundException("Not found username");
            }
            return new CustomUserDetails(user.get());
        }catch (final Exception e){
            throw new RuntimeException(e);
        }
    }
}
