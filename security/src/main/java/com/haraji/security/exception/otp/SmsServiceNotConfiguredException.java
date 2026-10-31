package com.haraji.security.exception.otp;

import com.haraji.common.constant.ErrorCode;
import com.haraji.common.exception.BaseException;
import org.springframework.http.HttpStatus;

public class SmsServiceNotConfiguredException extends BaseException {

    public SmsServiceNotConfiguredException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
                "otp.sms.service.not.configured");
    }
}
