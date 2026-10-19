package com.haraji.security.service.otp;

import com.haraji.security.config.OtpChannelProperties;
import com.haraji.security.constant.IdentifierType;
import com.haraji.security.constant.OtpPurpose;
import com.haraji.security.database.entity.UserIdentifierEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpDeliveryService {

    private final OtpChannelProperties channelProperties;
    private final EmailService emailService;
    private final ObjectProvider<SmsService> smsServiceProvider;

    public void send(
            UserIdentifierEntity identifier,
            String code,
            OtpPurpose purpose) {

        IdentifierType type = identifier.getType();

        if (type == IdentifierType.EMAIL) {
            if (!channelProperties.getEmail().isEnabled()) {
                throw new IllegalStateException("ارسال OTP از طریق ایمیل غیرفعال است.");
            }

            emailService.sendOtp(
                    identifier.getValue(),
                    code,
                    purpose
            );
            return;
        }

        if (type == IdentifierType.PHONE) {
            if (!channelProperties.getPhone().isEnabled()) {
                throw new IllegalStateException("ارسال OTP از طریق پیامک غیرفعال است.");
            }
            SmsService smsService = smsServiceProvider.getIfAvailable();

            if (smsService == null) {
                throw new IllegalStateException("کانال پیامک فعال است، اما پیاده‌سازی SmsService وجود ندارد.");
            }

            smsService.sendOtp(
                    identifier.getValue(),
                    code,
                    purpose
            );
            return;
        }

        throw new IllegalArgumentException("نوع شناسه برای ارسال OTP پشتیبانی نمی‌شود.");
    }
}
