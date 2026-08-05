package com.haraji.security.service;

import com.haraji.security.api.dto.UserEditRequestDto;
import com.haraji.security.api.dto.register.UserRequestDto;
import com.haraji.security.database.entity.PersonEntity;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.repository.PersonRepository;
import com.haraji.security.database.repository.UserRepository;
import com.haraji.security.exception.authentication.UserNotCreatedException;
import com.haraji.security.exception.authentication.UserNotFoundException;
import com.haraji.security.mapper.PersonMapper;
import com.haraji.security.mapper.UserMapper;
import com.haraji.security.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Validated
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final UserMapper userMapper;
    private final PersonMapper personMapper;

    @Override
    public List<User> getAll() {
        try {
            List<UserEntity> provinceEntityList = userRepository.findAll();
            if (!provinceEntityList.isEmpty())
                return userMapper.toModelList(provinceEntityList);
            return null;
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for userService.getAll()", e.getMessage());
            throw new UserNotFoundException();
        }
    }

    @Override
    public User getByName(String username) {
//        try {
//            Optional<UserEntity> optional = userRepository.findByUsername(username);
//            if (optional.isPresent())
//                return userMapper.toModel(optional.get());
//            throw new UserNotFoundException();
//        } catch (Exception e) {
//            log.info("\nThe exception '{}' was thrown for userService.getAll()", e.getMessage());
//            throw new UserNotFoundException();
//        }
        return null;
    }

    @Override
    public User getById(Long id) {
        try {
            Optional<UserEntity> optional = userRepository.findById(id);
            if (optional.isPresent())
                return userMapper.toModel(optional.get());
            throw new UserNotFoundException();
        } catch (Exception e) {
            log.info("\nThe exception '{}' was thrown for userService.getAll()", e.getMessage());
            throw new UserNotFoundException();
        }
    }

    @Override
    public User editUser(UserEditRequestDto userEditRequestDto) {
//        try {
//            UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
//            if (StringUtils.isNotBlank(userDetails.getUsername())) {
//                Optional<UserEntity> optional = userRepository.findByUsername(userDetails.getUsername());
//                if (optional.isPresent())
//                    return userMapper.toModel(optional.get());
//            }
//            throw new UserNotFoundException();
//        } catch (Exception e) {
//            log.info("\nThe exception '{}' was thrown for userService.getAll()", e.getMessage());
//            throw new UserNotFoundException();
//        }


        return null;
    }

    @Override
    public User editUserByAdmin(Long userId, UserEditRequestDto request) {
        return null;
    }

    @Override
    public List<User> searchUsername(String title) {
//        try {
//            Optional<List<UserEntity>> userEntities = userRepository.findAllByUsernameContains(title);
//            if (userEntities.isPresent())
//                return userMapper.toModelList(userEntities.get());
//            throw new UserNotFoundException();
//        } catch (Exception e) {
//            log.info("\nThe exception '{}' was thrown for userService.getAll()", e.getMessage());
//            throw new UserNotFoundException();
//        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createUser(UserRequestDto userRequestDto) {

        checkUserName(userRequestDto.getUsername());
        checkNationalCode(userRequestDto.getNationalCode());
        PersonEntity personEntity = personMapper.toEntity(userRequestDto);
        personRepository.save(personEntity);

        UserEntity userEntity = userMapper.toEntity(userRequestDto);
        userEntity.setPerson(personEntity);
//
//        userEntity.setRole(RoleType.VIEWER);
//
//        String hashedPassword = passwordEncoder.encode(userRequestDto.getPassword());
//        userEntity.setPassword(hashedPassword);
//        try {
//            userRepository.save(userEntity);
//        } catch (Exception e) {
//            log.info("\nThe exception '{}' was thrown for userService.createUser()", e.getMessage());
//            throw new UserNotCreatedException();
//        }
//        User user = userMapper.toModel(userEntity);
//        return createToken(user);
        return null;
    }


    private void checkNationalCode(String nationalCode) {
        personRepository.findByNationalCode(nationalCode).ifPresent(person -> {
            log.error("NationalCode already exists: {}", nationalCode);
            throw new UserNotCreatedException("national.code.exists", null);
        });
    }

    private void checkUserName(String username) {
//        userRepository.findByUsername(username).ifPresent(user -> {
//            log.error("Username already exists: {}", username);
//            throw new UserNotCreatedException("username.exists", null);
//        });
    }

    private String createToken(User user) {

//        return jwtUtil.generateToken(user);
        return null;

    }

}
