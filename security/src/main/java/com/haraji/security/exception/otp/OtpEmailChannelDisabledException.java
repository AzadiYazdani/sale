package com.haraji.security.exception.otp;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class OtpEmailChannelDisabledException extends BaseException {

    public OtpEmailChannelDisabledException() {
        super(
                ErrorCode.OTP_EMAIL_CHANNEL_DISABLED,
                HttpStatus.BAD_REQUEST,
                "otp.email.channel.disabled"
        );
    }
}
