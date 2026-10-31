
package com.haraji.security.service.user;

import com.haraji.common.util.DateUtil;
import com.haraji.security.database.entity.PersonEntity;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.repository.PersonRepository;
import com.haraji.security.database.repository.UserRepository;
import com.haraji.security.dto.user.AdminUserResponse;
import com.haraji.security.dto.user.UserEditRequest;
import com.haraji.security.dto.user.UserResponse;
import com.haraji.security.exception.authentication.UserNotFoundException;
import com.haraji.security.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final UserMapper userMapper;

    @Override
    public Page<AdminUserResponse> getAllByPaging(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toAdminUserResponse);
    }

    @Override
    public AdminUserResponse getById(Long id) {
        return userMapper.toAdminUserResponse(findUserById(id));
    }

    @Override
    public Page<AdminUserResponse> searchUsername(String username, Pageable pageable) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("عبارت جست‌وجو الزامی است.");
        }

        return userRepository.searchByIdentifier(username.trim(), pageable)
                .map(userMapper::toAdminUserResponse);
    }

    @Override
    public UserResponse getCurrentUser() {
        return userMapper.toUserResponse(findCurrentUser());
    }

    @Override
    @Transactional
    public UserResponse editUser(UserEditRequest request) {
        return userMapper.toUserResponse(updateUser(findCurrentUser(), request));
    }

    @Override
    @Transactional
    public AdminUserResponse editUserByAdmin(Long userId, UserEditRequest request) {
        return userMapper.toAdminUserResponse(updateUser(findUserById(userId), request));
    }

    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private UserEntity findCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof SecurityUser securityUser)) {
            throw new AccessDeniedException("کاربر احراز هویت نشده است.");
        }

        return findUserById(securityUser.getUser().getId());
    }

    private UserEntity updateUser(UserEntity user, UserEditRequest request) {
        PersonEntity person = user.getPerson();

        if (person == null) {
            person = PersonEntity.newInstance().setUser(user).build();
            user.setPerson(person);
        }

        validateUniqueFields(request, person);

        if (request.getEmail() != null) {
            person.setEmail(request.getEmail().trim());
        }
        if (request.getMobile() != null) {
            person.setTelephone(request.getMobile().trim());
        }
        if (request.getAddress() != null) {
            person.setAddress(request.getAddress().trim());
        }
        if (request.getFirstName() != null) {
            person.setFirstName(request.getFirstName().trim());
        }
        if (request.getLastName() != null) {
            person.setLastName(request.getLastName().trim());
        }
        if (request.getFatherName() != null) {
            person.setFatherName(request.getFatherName().trim());
        }
        if (request.getNationalCode() != null) {
            person.setNationalCode(request.getNationalCode().trim());
        }
        if (request.getIdentityNumber() != null) {
            person.setIdentityNumber(request.getIdentityNumber().trim());
        }
        if (StringUtils.hasText(request.getBirthDate())) {
            person.setBirthDate(DateUtil.getLocalDateFromString(request.getBirthDate().trim()));
        }
        if (request.getBirthCity() != null) {
            person.setBirthCity(request.getBirthCity().trim());
        }

        personRepository.save(person);

        return userRepository.save(user);
    }

    private void validateUniqueFields(UserEditRequest request, PersonEntity currentPerson) {
        Long currentPersonId = currentPerson.getId();

        if (StringUtils.hasText(request.getNationalCode())) {
            personRepository.findByNationalCode(request.getNationalCode().trim())
                    .filter(person -> !Objects.equals(person.getId(), currentPersonId))
                    .ifPresent(person -> {
                        throw new IllegalArgumentException("این کد ملی قبلاً ثبت شده است.");
                    });
        }

        if (StringUtils.hasText(request.getEmail())) {
            personRepository.findByEmail(request.getEmail().trim())
                    .filter(person -> !Objects.equals(person.getId(), currentPersonId))
                    .ifPresent(person -> {
                        throw new IllegalArgumentException("این ایمیل قبلاً برای شخص دیگری ثبت شده است.");
                    });
        }

        if (StringUtils.hasText(request.getMobile())) {
            personRepository.findByMobile(request.getMobile().trim())
                    .filter(person -> !Objects.equals(person.getId(), currentPersonId))
                    .ifPresent(person -> {
                        throw new IllegalArgumentException("این شماره همراه قبلاً برای شخص دیگری ثبت شده است.");
                    });
        }
    }
}
