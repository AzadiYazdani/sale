package com.haraji.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "security.otp")
@Getter
@Setter
public class OtpProperties {

    /**
     * تعداد ارقام کد
     */
    private int length;

    /**
     * مدت اعتبار کد (دقیقه)
     */
    private int expirationMinutes;

    /**
     * حداقل فاصله بین دو درخواست OTP (ثانیه)
     */
    private int resendSeconds;

    /**
     * حداکثر تعداد تلاش برای ورود کد
     */
    private int maxAttempts;

    /**
     * حداکثر تعداد ارسال OTP در یک روز
     */
    private int maxPerDay;
}
