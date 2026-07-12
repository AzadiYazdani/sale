package com.haraji.security.service;


import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserById(Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new DisabledException("User is disabled");
        }

        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedException("User is locked");
        }

        return new SecurityUser(user);
    }

}
