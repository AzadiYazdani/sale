package com.haraji.security.service;

import com.haraji.security.api.dto.UserRequestDto;
import com.haraji.security.constant.RoleEnum;
import com.haraji.security.database.entity.PersonEntity;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.repository.PersonRepository;
import com.haraji.security.database.repository.UserRepository;
import com.haraji.security.exception.authentication.UserNotCreatedException;
import com.haraji.security.exception.authentication.UserNotFoundException;
import com.haraji.security.mapper.PersonMapper;
import com.haraji.security.mapper.UserMapper;
import com.haraji.security.model.Person;
import com.haraji.security.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Slf4j
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final UserMapper userMapper;
    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PersonRepository personRepository, UserMapper userMapper, PersonMapper personMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.userMapper = userMapper;
        this.personMapper = personMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAll() {
        try {
            List<UserEntity> stateEntityList = userRepository.findAll();
            if (!stateEntityList.isEmpty())
                return userMapper.toModelList(stateEntityList);
            return null;
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for userService.getAll()", e.getMessage());
            throw new UserNotFoundException();
        }
    }

    @Override
    public User getByName(String username) {
        try {
            UserEntity userEntity = userRepository.findByUsername(username).get();
            return userMapper.toModel(userEntity);
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for userService.getAll()", e.getMessage());
            throw new UserNotFoundException();
        }
    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(UserRequestDto userRequestDto) {

        checkUserName(userRequestDto.getUsername());
        checkNationalCode(userRequestDto.getNationalCode());
        PersonEntity personEntity = personMapper.toEntity(userRequestDto);
        personRepository.save(personEntity);

        UserEntity userEntity = userMapper.toEntity(userRequestDto);
        userEntity.setPerson(personEntity);
        userEntity.setRole(RoleEnum.VIEWER);

        String hashedPassword = passwordEncoder.encode(userRequestDto.getPassword());
        userEntity.setPassword(hashedPassword);
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for userService.createUser()", e.getMessage());
            throw new UserNotCreatedException();
        }
        User user = userMapper.toModel(userEntity);
        Person person = personMapper.toModel(personEntity);
        user.setPerson(person);
        return user;
    }

    private void checkNationalCode(String nationalCode) {
        personRepository.findByNationalCode(nationalCode).ifPresent(person -> {
            log.error("NationalCode already exists: {}", nationalCode);
            throw new UserNotCreatedException("NationalCode exists ");
        });
    }

    private void checkUserName(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            log.error("Username already exists: {}", username);
            throw new UserNotCreatedException("UserName exists ");
        });
    }
}
