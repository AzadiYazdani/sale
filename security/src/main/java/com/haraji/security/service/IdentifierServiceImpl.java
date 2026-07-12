package com.haraji.security.service;

import com.haraji.security.constant.IdentifierType;
import com.haraji.security.database.entity.UserIdentifierEntity;
import com.haraji.security.database.repository.UserIdentifierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdentifierServiceImpl
        implements IdentifierService {

    private final UserIdentifierRepository repository;

    @Override
    public UserIdentifierEntity save(UserIdentifierEntity identifier) {
        return repository.save(identifier);
    }

    @Override
    public Optional<UserIdentifierEntity> find(IdentifierType type,String value) {
        return repository.findByTypeAndValue(type, value);
    }

    @Override
    public boolean exists(IdentifierType type, String value) {
        return repository.existsByTypeAndValue(type, value);
    }
}
