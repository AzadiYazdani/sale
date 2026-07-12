package com.haraji.security.service;

import com.haraji.security.constant.IdentifierType;
import com.haraji.security.database.entity.UserIdentifierEntity;

import java.util.Optional;

public interface IdentifierService {
    UserIdentifierEntity save(UserIdentifierEntity identifier);

    Optional<UserIdentifierEntity> find(IdentifierType type, String value);

    boolean exists(IdentifierType type, String value);
}
