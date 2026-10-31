
package com.haraji.security.service.otp;

import com.haraji.common.exception.InvalidFormatException;
import com.haraji.security.config.OtpChannelProperties;
import com.haraji.security.constant.IdentifierType;
import com.haraji.security.constant.OtpPurpose;
import com.haraji.security.database.entity.UserIdentifierEntity;
import com.haraji.security.exception.otp.OtpEmailChannelDisabledException;
import com.haraji.security.exception.otp.OtpPhoneChannelDisabledException;
import com.haraji.security.exception.otp.SmsServiceNotConfiguredException;
import com.haraji.security.exception.otp.UnsupportedOtpIdentifierTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OtpDeliveryService {

    private final OtpChannelProperties channelProperties;
    private final EmailService emailService;
    private final ObjectProvider<SmsService> smsServiceProvider;

    public void send(UserIdentifierEntity identifier, String code, OtpPurpose purpose) {

        if (identifier == null
                || identifier.getType() == null
                || identifier.getValue() == null
                || identifier.getValue().isBlank()
                || code == null
                || code.isBlank()
                || purpose == null) {
            throw new InvalidFormatException("اطلاعات ارسال کد تأیید معتبر نیست.");
        }

        IdentifierType type = identifier.getType();

        if (type == IdentifierType.EMAIL) {
            if (!channelProperties.getEmail().isEnabled()) {
                throw new OtpEmailChannelDisabledException();
            }

            emailService.sendOtp(identifier.getValue(), code, purpose);
            return;
        }

        if (type == IdentifierType.PHONE) {
            if (!channelProperties.getPhone().isEnabled()) {
                throw new OtpPhoneChannelDisabledException();
            }

            SmsService smsService = smsServiceProvider.getIfAvailable();

            if (smsService == null) {
                throw new SmsServiceNotConfiguredException();
            }

            smsService.sendOtp(identifier.getValue(), code, purpose);
            return;
        }

        throw new UnsupportedOtpIdentifierTypeException();
    }
}
