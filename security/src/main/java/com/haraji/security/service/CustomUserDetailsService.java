package com.haraji.security.service;


import com.haraji.security.constant.LoginType;
import com.haraji.security.database.entity.PersonEntity;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.repository.PersonRepository;
import com.haraji.security.database.repository.UserRepository;
import com.haraji.security.exception.authentication.UserNotFoundException;
import com.haraji.security.util.CommonUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;

    public CustomUserDetailsService(UserRepository userRepository, PersonRepository personRepository) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {

        LoginType type = CommonUtil.detectLoginType(identifier);
        String normalizedIdentifier = CommonUtil.normalizeIdentifier(identifier, type);
        UserEntity userEntity = findUserByType(normalizedIdentifier, type);

        return new org.springframework.security.core.userdetails.User(
                normalizedIdentifier,
                userEntity.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole().toString()))
        );
    }

    private UserEntity findUserByType(String normalizedIdentifier, LoginType type) {
        return switch (type) {
            case EMAIL -> findByEmail(normalizedIdentifier);
            case PHONE -> findByMobile(normalizedIdentifier);
        };
    }

    private UserEntity findByEmail(String email){
        Optional<PersonEntity> personEntity = personRepository.findByEmail(email);
        if(personEntity.isPresent())
            return personEntity.get().getUser();

          throw new UserNotFoundException();
    }

    private UserEntity findByMobile(String mobile){
        Optional<PersonEntity> personEntity = personRepository.findByMobile(mobile);
        if(personEntity.isPresent())
            return personEntity.get().getUser();

        throw new UserNotFoundException();
    }

}
