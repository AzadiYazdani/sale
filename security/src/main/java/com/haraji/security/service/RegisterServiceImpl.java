package com.haraji.security.service;

import com.haraji.security.api.dto.otp.VerifyOtpRequest;
import com.haraji.security.api.dto.otp.VerifyOtpResponse;
import com.haraji.security.api.dto.register.RegisterRequest;
import com.haraji.security.api.dto.register.RegisterResponse;
import com.haraji.security.constant.OtpPurpose;
import com.haraji.security.constant.RoleType;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.entity.UserIdentifierEntity;
import com.haraji.security.database.repository.UserIdentifierRepository;
import com.haraji.security.database.repository.UserRepository;
import com.haraji.security.exception.authentication.DuplicateIdentifierException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final UserIdentifierRepository identifierRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        if (identifierRepository.existsByTypeAndValue(
                request.getIdentifierType(),
                request.getIdentifier())) {

            throw new DuplicateIdentifierException(request.getIdentifierType());
        }

        UserEntity user = UserEntity.newInstance()
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(RoleType.VIEWER)
                .enabled(false)
                .locked(false)
                .build();
        user = userRepository.save(user);

        UserIdentifierEntity identifier = UserIdentifierEntity.newInstance()
                .user(user)
                .type(request.getIdentifierType())
                .value(request.getIdentifier())
                .verified(false)
                .primaryIdentifier(true)
                .enabled(true)
                .build();
        identifier = identifierRepository.save(identifier);

        otpService.generateOtp(identifier, OtpPurpose.REGISTER);
        return RegisterResponse.newInstance()
                .userId(user.getId())
                .message("Verification code has been sent.")
                .build();
    }

    @Override
    @Transactional
    public VerifyOtpResponse verifyRegisterOtp(VerifyOtpRequest request) {

        UserIdentifierEntity identifier =
                identifierRepository
                        .findByTypeAndValue(
                                request.getIdentifierType(),
                                request.getIdentifier()
                        )
                        .orElseThrow(() ->
                                new RuntimeException("Identifier not found"));

        if (identifier.getVerified()) {
            throw new RuntimeException("Identifier already verified");
        }

        otpService.verifyOtp(
                identifier,
                OtpPurpose.REGISTER,
                request.getCode()
        );

        identifier.setVerified(true);
        identifierRepository.save(identifier);

        UserEntity user = identifier.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        return VerifyOtpResponse.builder()
                .userId(user.getId())
                .message("Registration completed successfully")
                .build();

    }

}
