package com.haraji.security.service;

import com.haraji.security.constant.OtpPurpose;

public interface SmsService {
    void sendOtp(String phone, String code, OtpPurpose purpose);
}
