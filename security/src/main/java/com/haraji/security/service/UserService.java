package com.haraji.security.service;

import com.haraji.security.api.dto.UserEditRequestDto;
import com.haraji.security.api.dto.register.UserRequestDto;
import com.haraji.security.model.User;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface UserService {

    List<User> getAll();

    User getByName(String username);

    User getById(Long id);

    User editUser(UserEditRequestDto userEditRequestDto);

    String createUser(UserRequestDto userRequest);

    List<User> searchUsername(String title);

    String login(String userName, String password);
}
