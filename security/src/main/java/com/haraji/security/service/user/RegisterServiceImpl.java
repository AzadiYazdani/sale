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
import com.haraji.security.exception.authentication.DuplicateIdentifierException;
import com.haraji.security.service.otp.OtpService;
import com.haraji.security.util.CommonUtil;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        boolean otpEnabled = registrationProperties.isOtpEnabled();

        IdentifierType type = otpEnabled ? request.getIdentifierType() : IdentifierType.USERNAME;
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
                .message(otpEnabled ? "کد تأیید برای شناسه ثبت‌شده ارسال شد." : "ثبت‌نام با موفقیت انجام شد.")
                .build();
    }

    @Override
    @Transactional
    public VerifyOtpResponse verifyRegisterOtp(VerifyOtpRequest request) {
        IdentifierType type = request.getIdentifierType();

        String value = CommonUtil.normalizeIdentifier(request.getIdentifier(), type);
        validateChannelEnabled(type);

        UserIdentifierEntity identifier =
                userIdentifierRepository.findByTypeAndValue(type, value)
                        .orElseThrow(() ->
                                new IllegalArgumentException("ثبت‌ نامی با این شناسه پیدا نشد.")
                        );

        if (Boolean.TRUE.equals(identifier.getVerified())) {
            throw new IllegalStateException("این شناسه قبلاً تأیید شده است.");
        }

        otpService.verifyOtp(identifier,OtpPurpose.REGISTER,request.getCode());

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

    private void validateChannelEnabled(IdentifierType type) {
        if (type == IdentifierType.EMAIL
                && !otpChannelProperties.getEmail().isEnabled()) {
            throw new IllegalStateException("ثبت‌نام از طریق ایمیل غیرفعال است.");
        }

        if (type == IdentifierType.PHONE
                && !otpChannelProperties.getPhone().isEnabled()) {
            throw new IllegalStateException("ثبت‌نام از طریق شماره تلفن غیرفعال است.");
        }
    }
}
