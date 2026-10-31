package com.haraji.security.service.user;

import com.haraji.security.dto.user.AdminUserResponse;
import com.haraji.security.dto.user.UserEditRequest;
import com.haraji.security.dto.user.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserService {

    Page<AdminUserResponse> getAllByPaging(Pageable pageable);

    AdminUserResponse getById(@Min(1) Long id);

    Page<AdminUserResponse> searchUsername(String username, Pageable pageable);

    UserResponse getCurrentUser();

    UserResponse editUser(@Valid UserEditRequest request);

    AdminUserResponse editUserByAdmin(@Min(1) Long userId, @Valid UserEditRequest request);
}
