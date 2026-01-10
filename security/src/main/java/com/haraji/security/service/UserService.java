package com.haraji.security.service;

import com.haraji.security.api.dto.UserRequestDto;
import com.haraji.security.model.User;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface UserService {

    List<User> getAll();

    User getByName(String username);

    User getById(int id);

    User createUser(UserRequestDto userRequest);
}
