
package com.haraji.security.service.user;

import com.haraji.security.config.OtpChannelProperties;
import com.haraji.security.config.RegistrationProperties;
import com.haraji.security.constant.IdentifierType;
import com.haraji.security.constant.OtpPurpose;
import com.haraji.security.constant.RoleType;
import com.haraji.security.database.entity.UserEntity;
import com.haraji.security.database.entity.UserIdentifierEntity;
import com.haraji.security.database.repository.UserIdentifierRepository;
import com.haraji.security.database.repository.UserRepository;
import com.haraji.security.dto.otp.VerifyOtpRequest;
import com.haraji.security.dto.otp.VerifyOtpResponse;
import com.haraji.security.dto.register.RegisterRequest;
import com.haraji.security.dto.register.RegisterResponse;
import com.haraji.security.exception.authentication.registration.DuplicateIdentifierException;
import com.haraji.security.exception.authentication.registration.IdentifierAlreadyVerifiedException;
import com.haraji.security.exception.authentication.registration.IdentifierNotFoundException;
import com.haraji.security.exception.authentication.registration.InvalidRegistrationRequestException;
import com.haraji.security.exception.authentication.registration.RegistrationIdentifierModeException;
import com.haraji.security.exception.otp.OtpEmailChannelDisabledException;
import com.haraji.security.exception.otp.OtpPhoneChannelDisabledException;
import com.haraji.security.service.otp.OtpService;
import com.haraji.security.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;
    private final UserIdentifierRepository userIdentifierRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final RegistrationProperties registrationProperties;
    private final OtpChannelProperties otpChannelProperties;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        validateRegisterRequest(request);

        boolean otpEnabled = registrationProperties.isOtpEnabled();
        IdentifierType type = request.getIdentifierType();

        validateIdentifierMode(type, otpEnabled);

        String value = CommonUtil.normalizeIdentifier(request.getIdentifier(), type);

        if (otpEnabled) {
            validateChannelEnabled(type);
        }

        if (userIdentifierRepository.existsByTypeAndValue(type, value)) {
            throw new DuplicateIdentifierException(type);
        }

        UserEntity user = new UserEntity();
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(RoleType.VIEWER);
        user.setEnabled(!otpEnabled);
        user.setLocked(false);
        user = userRepository.save(user);

        UserIdentifierEntity identifier = new UserIdentifierEntity();
        identifier.setUser(user);
        identifier.setType(type);
        identifier.setValue(value);
        identifier.setVerified(false);
        identifier.setPrimaryIdentifier(true);
        identifier.setEnabled(true);
        identifier = userIdentifierRepository.save(identifier);

        if (otpEnabled) {
            otpService.generateOtp(identifier, OtpPurpose.REGISTER);
        }

        return RegisterResponse.newInstance()
                .userId(user.getId())
                .message(otpEnabled
                        ? "کد تأیید برای شناسه ثبت‌شده ارسال شد."
                        : "ثبت‌نام با موفقیت انجام شد.")
                .build();
    }

    @Override
    @Transactional
    public VerifyOtpResponse verifyRegisterOtp(VerifyOtpRequest request) {
        validateVerifyOtpRequest(request);

        IdentifierType type = request.getIdentifierType();
        String value = CommonUtil.normalizeIdentifier(request.getIdentifier(), type);

        validateChannelEnabled(type);

        UserIdentifierEntity identifier = userIdentifierRepository
                .findByTypeAndValue(type, value)
                .orElseThrow(IdentifierNotFoundException::new);

        if (Boolean.TRUE.equals(identifier.getVerified())) {
            throw new IdentifierAlreadyVerifiedException();
        }

        otpService.verifyOtp(identifier, OtpPurpose.REGISTER, request.getCode());

        identifier.setVerified(true);
        userIdentifierRepository.save(identifier);

        UserEntity user = identifier.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        return VerifyOtpResponse.newInstance()
                .userId(user.getId())
                .message("شناسه با موفقیت تأیید شد.")
                .build();
    }

    private void validateRegisterRequest(RegisterRequest request) {
        if (request == null
                || request.getIdentifierType() == null
                || request.getIdentifier() == null
                || request.getIdentifier().isBlank()
                || request.getPassword() == null
                || request.getPassword().isBlank()) {
            throw new InvalidRegistrationRequestException();
        }
    }

    private void validateVerifyOtpRequest(VerifyOtpRequest request) {
        if (request == null
                || request.getIdentifierType() == null
                || request.getIdentifier() == null
                || request.getIdentifier().isBlank()
                || request.getCode() == null
                || request.getCode().isBlank()) {
            throw new InvalidRegistrationRequestException();
        }
    }

    private void validateChannelEnabled(IdentifierType type) {
        if (type == IdentifierType.EMAIL && !otpChannelProperties.getEmail().isEnabled()) {
            throw new OtpEmailChannelDisabledException();
        }

        if (type == IdentifierType.PHONE && !otpChannelProperties.getPhone().isEnabled()) {
            throw new OtpPhoneChannelDisabledException();
        }
    }

    private void validateIdentifierMode(IdentifierType type, boolean otpEnabled) {
        if (otpEnabled) {
            if (type == IdentifierType.USERNAME) {
                throw new RegistrationIdentifierModeException();
            }
            return;
        }

        boolean allowed = switch (type) {
            case EMAIL -> registrationProperties.getAllowedIdentifierModes().contains(RegistrationProperties.IdentifierMode.EMAIL)
                    || registrationProperties.getAllowedIdentifierModes().contains(RegistrationProperties.IdentifierMode.EMAIL_AND_PHONE);

            case PHONE -> registrationProperties.getAllowedIdentifierModes().contains(RegistrationProperties.IdentifierMode.PHONE)
                    || registrationProperties.getAllowedIdentifierModes().contains(RegistrationProperties.IdentifierMode.EMAIL_AND_PHONE);

            case USERNAME -> registrationProperties.getAllowedIdentifierModes().contains(RegistrationProperties.IdentifierMode.USERNAME);
        };

        if (!allowed) {
            throw new RegistrationIdentifierModeException();
        }
    }
}
