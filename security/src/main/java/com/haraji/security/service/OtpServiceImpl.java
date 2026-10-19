package com.haraji.security.service;

import com.haraji.security.config.OtpProperties;
import com.haraji.security.constant.OtpPurpose;
import com.haraji.security.database.entity.OtpEntity;
import com.haraji.security.database.entity.UserIdentifierEntity;
import com.haraji.security.database.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final OtpProperties otpProperties;
    private final OtpRepository otpRepository;
    private final OtpDeliveryService otpDeliveryService;

    @Override
    @Transactional
    public void generateOtp(UserIdentifierEntity identifier, OtpPurpose purpose) {
        if (identifier == null || identifier.getId() == null) {
            throw new IllegalArgumentException("شناسه کاربر برای تولید OTP معتبر نیست.");
        }

        if (purpose == null) {
            throw new IllegalArgumentException("هدف تولید OTP مشخص نشده است.");
        }

        LocalDateTime now = LocalDateTime.now();

        // محدودیت فاصله زمانی درخواست مجدد
        Optional<OtpEntity> lastOtp =
                otpRepository.findTopByIdentifierIdAndPurposeOrderByIdDesc(
                        identifier.getId(), purpose
                );

        if (lastOtp.isPresent() && lastOtp.get().getCreatedDate() != null) {
            LocalDateTime nextAllowedTime = lastOtp.get()
                    .getCreatedDate()
                    .plusSeconds(otpProperties.getResendSeconds());

            if (nextAllowedTime.isAfter(now)) {
                throw new IllegalStateException("برای درخواست کد جدید هنوز باید کمی صبر کنید.");
            }
        }

        // محدودیت تعداد درخواست روزانه برای هر شناسه، در تمام هدف‌ها
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime startOfTomorrow = startOfDay.plusDays(1);

        long dailyCount = otpRepository
                .countByIdentifierIdAndCreatedDateGreaterThanEqualAndCreatedDateLessThan(
                        identifier.getId(),
                        startOfDay,
                        startOfTomorrow
                );

        if (dailyCount >= otpProperties.getMaxPerDay()) {
            throw new IllegalStateException("تعداد درخواست‌های کد تأیید امروز به حد مجاز رسیده است.");
        }

        String code = generateCode();

        OtpEntity otp = new OtpEntity();
        otp.setIdentifier(identifier);
        otp.setPurpose(purpose);
        otp.setCode(code);
        otp.setExpireAt(now.plusMinutes(otpProperties.getExpirationMinutes()));
        otp.setAttemptCount(0);
        otp.setUsed(false);

        otpRepository.save(otp);

        // ارسال کد از طریق کانال فعال‌شده در تنظیمات
        otpDeliveryService.send(identifier, code, purpose);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = IllegalArgumentException.class)
    public void verifyOtp(UserIdentifierEntity identifier, OtpPurpose purpose, String code) {

        if (identifier == null || identifier.getId() == null) {
            throw new IllegalArgumentException("شناسه کاربر برای تأیید OTP معتبر نیست.");
        }
        if (purpose == null) {
            throw new IllegalArgumentException("هدف تأیید OTP مشخص نشده است.");
        }
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("کد تأیید الزامی است.");
        }

        OtpEntity otp = getLastOtp(identifier.getId(), purpose)
                .orElseThrow(() ->
                        new IllegalArgumentException("کد تأیید معتبر پیدا نشد.")
                );

        LocalDateTime now = LocalDateTime.now();

        if (!otp.getExpireAt().isAfter(now)) {
            otp.setUsed(true);
            otpRepository.save(otp);
            throw new IllegalArgumentException("مهلت استفاده از کد تأیید به پایان رسیده است.");
        }

        if (otp.getAttemptCount() >= otpProperties.getMaxAttempts()) {
            otp.setUsed(true);
            otpRepository.save(otp);
            throw new IllegalArgumentException("تعداد تلاش‌های مجاز برای این کد به پایان رسیده است.");
        }

        if (!otp.getCode().equals(code.trim())) {
            int attempts = otp.getAttemptCount() + 1;
            otp.setAttemptCount(attempts);

            if (attempts >= otpProperties.getMaxAttempts()) {
                otp.setUsed(true);
            }
            otpRepository.save(otp);
            throw new IllegalArgumentException(
                    attempts >= otpProperties.getMaxAttempts()
                            ? "تعداد تلاش‌های مجاز برای این کد به پایان رسیده است."
                            : "کد تأیید صحیح نیست."
            );
        }

        otp.setUsed(true);
        otpRepository.save(otp);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OtpEntity> getLastOtp(
            Long identifierId,
            OtpPurpose purpose) {

        return otpRepository.findTopByIdentifierIdAndPurposeAndUsedFalseOrderByIdDesc(
                identifierId,
                purpose
        );
    }

    private String generateCode() {
        int length = otpProperties.getLength();

        if (length < 1 || length > 9) {
            throw new IllegalStateException("طول OTP باید بین ۱ تا ۹ رقم باشد.");
        }

        int bound = (int) Math.pow(10, length);
        int number = SECURE_RANDOM.nextInt(bound);

        return String.format(Locale.ROOT, "%0" + length + "d", number);
    }
}
