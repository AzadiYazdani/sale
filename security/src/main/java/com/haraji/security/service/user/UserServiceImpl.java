package com.haraji.security.service.user;

import com.haraji.common.util.DateUtil;
import com.haraji.security.constant.IdentifierType;
import com.haraji.security.constant.RoleType;
import com.haraji.security.database.entity.PersonEntity;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.repository.PersonRepository;
import com.haraji.security.database.repository.UserRepository;
import com.haraji.security.dto.user.AdminUserResponse;
import com.haraji.security.dto.user.UserAccessUpdateRequest;
import com.haraji.security.dto.user.UserEditRequest;
import com.haraji.security.dto.user.UserResponse;
import com.haraji.security.exception.authentication.registration.DuplicateIdentifierException;
import com.haraji.security.exception.authentication.registration.DuplicateNationalCodeException;
import com.haraji.security.exception.authentication.UserNotFoundException;
import com.haraji.security.mapper.UserMapper;
import com.haraji.security.service.token.RefreshTokenService;
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
    private final RefreshTokenService refreshTokenService;
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
    public Page<AdminUserResponse> searchUsers(String keyword, Pageable pageable) {
        if (!StringUtils.hasText(keyword)) {
            throw new IllegalArgumentException("Search term is required.");
        }

        return userRepository.searchUsers(keyword.trim(), pageable)
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
            throw new AccessDeniedException("User is not authenticated.");
        }

        return findUserById(securityUser.getUser().getId());
    }

    private UserEntity updateUser(UserEntity user, UserEditRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("User edit request is required.");
        }

        PersonEntity person = user.getPerson();

        if (person == null) {
            person = PersonEntity.newInstance().setUser(user).build();
            user.setPerson(person);
        }

        validateUniqueFields(request, person);

        if (StringUtils.hasText(request.getEmail())) {
            person.setEmail(request.getEmail().trim());
        }

        if (StringUtils.hasText(request.getMobile())) {
            person.setMobile(request.getMobile().trim());
        }

        if (StringUtils.hasText(request.getAddress())) {
            person.setAddress(request.getAddress().trim());
        }

        if (StringUtils.hasText(request.getFirstName())) {
            person.setFirstName(request.getFirstName().trim());
        }

        if (StringUtils.hasText(request.getLastName())) {
            person.setLastName(request.getLastName().trim());
        }

        if (StringUtils.hasText(request.getFatherName())) {
            person.setFatherName(request.getFatherName().trim());
        }

        if (StringUtils.hasText(request.getNationalCode())) {
            person.setNationalCode(request.getNationalCode().trim());
        }

        if (StringUtils.hasText(request.getIdentityNumber())) {
            person.setIdentityNumber(request.getIdentityNumber().trim());
        }

        if (StringUtils.hasText(request.getBirthDate())) {
            person.setBirthDate(DateUtil.getLocalDateFromString(request.getBirthDate().trim()));
        }

        if (StringUtils.hasText(request.getBirthCity())) {
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
                        throw new DuplicateNationalCodeException();
                    });
        }

        if (StringUtils.hasText(request.getEmail())) {
            personRepository.findByEmail(request.getEmail().trim())
                    .filter(person -> !Objects.equals(person.getId(), currentPersonId))
                    .ifPresent(person -> {
                        throw new DuplicateIdentifierException(IdentifierType.EMAIL);
                    });
        }

        if (StringUtils.hasText(request.getMobile())) {
            personRepository.findByMobile(request.getMobile().trim())
                    .filter(person -> !Objects.equals(person.getId(), currentPersonId))
                    .ifPresent(person -> {
                        throw new DuplicateIdentifierException(IdentifierType.PHONE);
                    });
        }
    }

    @Override
    @Transactional
    public AdminUserResponse updateUserAccess(Long userId, UserAccessUpdateRequest request) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("شناسه کاربر معتبر نیست.");
        }

        if (request == null || (request.getRole() == null
                && request.getEnabled() == null
                && request.getLocked() == null)) {
            throw new IllegalArgumentException("حداقل یکی از فیلدهای دسترسی باید مشخص شود.");
        }

        UserEntity currentUser = findCurrentUser();

        if (Objects.equals(currentUser.getId(), userId)) {
            throw new AccessDeniedException("امکان تغییر دسترسی حساب کاربری خودتان وجود ندارد.");
        }

        UserEntity targetUser = findUserById(userId);

        RoleType newRole = request.getRole() != null
                ? request.getRole()
                : targetUser.getRole();

        boolean newEnabled = request.getEnabled() != null
                ? request.getEnabled()
                : Boolean.TRUE.equals(targetUser.getEnabled());

        boolean newLocked = request.getLocked() != null
                ? request.getLocked()
                : Boolean.TRUE.equals(targetUser.getLocked());

        // حساب مسدود باید حتماً غیرفعال باشد.
        if (newLocked) {
            newEnabled = false;
        }

        boolean currentlyActiveAdmin = targetUser.getRole() == RoleType.ADMIN
                && Boolean.TRUE.equals(targetUser.getEnabled())
                && !Boolean.TRUE.equals(targetUser.getLocked());

        boolean willRemainActiveAdmin = newRole == RoleType.ADMIN && newEnabled;

        // جلوگیری از غیرفعال شدن یا تغییر نقش آخرین مدیر فعال
        if (currentlyActiveAdmin && !willRemainActiveAdmin
                && userRepository.countByRoleAndEnabledTrueAndLockedFalse(RoleType.ADMIN) <= 1) {
            throw new IllegalStateException(
                    "امکان غیرفعال‌کردن، قفل‌کردن یا تغییر نقش آخرین مدیر فعال وجود ندارد."
            );
        }

        targetUser.setRole(newRole);
        targetUser.setEnabled(newEnabled);
        targetUser.setLocked(newLocked);

        UserEntity savedUser = userRepository.save(targetUser);

        // باطل کردن تمام Refresh Tokenهای کاربر پس از تغییر دسترسی
        refreshTokenService.revokeAll(savedUser.getId());

        return userMapper.toAdminUserResponse(savedUser);
    }


}
