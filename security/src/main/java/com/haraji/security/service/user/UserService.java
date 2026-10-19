package com.haraji.security.service.user;

import com.haraji.security.dto.user.UserEditRequest;
import com.haraji.security.dto.user.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface UserService {

    Page<UserResponse> getAllByPaging(Pageable pageable);

    UserResponse getById(@Min(1) Long id);

    UserResponse getCurrentUser();

    UserResponse editUser(@Valid UserEditRequest request);

    UserResponse editUserByAdmin(@Min(1) Long userId, @Valid UserEditRequest request);

    List<UserResponse> searchUsername(String username);
}
