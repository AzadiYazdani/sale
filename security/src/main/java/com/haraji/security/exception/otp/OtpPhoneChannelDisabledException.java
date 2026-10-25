package com.haraji.security.exception.otp;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class OtpPhoneChannelDisabledException extends BaseException {

    public OtpPhoneChannelDisabledException() {
        super(
                ErrorCode.OTP_PHONE_CHANNEL_DISABLED,
                HttpStatus.BAD_REQUEST,
                "otp.phone.channel.disabled"
        );
    }
}
