package com.haraji.security.service;

import com.haraji.security.config.OtpProperties;
import com.haraji.security.constant.OtpPurpose;
import com.haraji.security.database.entity.OtpEntity;
import com.haraji.security.database.entity.UserIdentifierEntity;
import com.haraji.security.database.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final OtpProperties otpProperties;
    private final OtpRepository otpRepository;

    @Override
    @Transactional
    public void generateOtp(UserIdentifierEntity identifier,
                            OtpPurpose purpose) {

        OtpEntity otp = new OtpEntity();
        otp.setIdentifier(identifier);
        otp.setPurpose(purpose);
        otp.setCode(generateCode());
        otp.setExpireAt(LocalDateTime.now().plusMinutes(otpProperties.getExpirationMinutes()));
        otp.setUsed(false);
        otpRepository.save(otp);

        /*
         * اینجا بعداً
         * SmsService
         * EmailService
         * فراخوانی می‌شود.
         */
    }
    @Override
    @Transactional
    public void verifyOtp(UserIdentifierEntity identifier,
                          OtpPurpose purpose,
                          String code) {

        OtpEntity otp = otpRepository
                .findTopByIdentifierIdAndPurposeAndUsedFalseOrderByIdDesc(
                        identifier.getId(),
                        purpose)
                .orElseThrow(() ->
                        new RuntimeException("OTP not found"));

        if (otp.getExpireAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!otp.getCode().equals(code)) {
            throw new RuntimeException("OTP is invalid");
        }

        otp.setUsed(true);
        otpRepository.save(otp);
    }

    /**
     * تولید OTP شش رقمی
     */
    private String generateCode() {
        int length = otpProperties.getLength();
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length);
        int number = SECURE_RANDOM.nextInt(max - min) + min;

        return String.valueOf(number);
    }

    @Override
    public Optional<OtpEntity> getLastOtp(Long identifierId, OtpPurpose purpose) {
        return otpRepository
                .findTopByIdentifierIdAndPurposeAndUsedFalseOrderByIdDesc(
                        identifierId,
                        purpose
                );
    }

}
