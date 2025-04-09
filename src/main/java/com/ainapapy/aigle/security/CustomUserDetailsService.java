package com.ainapapy.aigle.security;

import com.ainapapy.aigle.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> new UserPrincipal(
                        user.getId(), 
                        user.getEmail(), 
                        user.getPassword(),
                        user.getRole().toString()
                ))
                .orElseThrow(()-> new UsernameNotFoundException("User not found with email: " + email) );
    }
    
}
