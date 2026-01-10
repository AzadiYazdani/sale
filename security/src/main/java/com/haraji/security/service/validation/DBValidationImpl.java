package com.haraji.security.service.validation;

import com.haraji.security.service.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("DB")
public class DBValidationImpl implements ValidationService {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public DBValidationImpl(PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public boolean validate(String username, String password) {
        try {
            String hashedPassword = passwordEncoder.encode(password);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            return Objects.equals(userDetails.getPassword(), hashedPassword);
        } catch (Exception e) {
            return false;
        }
    }

}
