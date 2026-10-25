package com.haraji.security.service.otp;

import com.haraji.security.constant.OtpPurpose;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final ObjectProvider<JavaMailSender> mailSenderProvider;

    @Value("${spring.mail.username:}")
    private String fromEmail;

    public void sendOtp(String email, String code, OtpPurpose purpose) {
        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();

        if (mailSender == null || fromEmail.isBlank()) {
            throw new IllegalStateException("تنظیمات ارسال ایمیل در application.yml کامل نیست.");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject(getSubject(purpose));
        message.setText(
                "کد تأیید شما: " + code
                        + "\n\nاعتبار این کد محدود است. "
                        + "از در اختیار گذاشتن آن به دیگران خودداری کنید."
        );

        mailSender.send(message);
    }

    private String getSubject(OtpPurpose purpose) {
        return switch (purpose) {
            case REGISTER -> "کد تأیید ثبت‌نام";
            case LOGIN -> "کد ورود به حساب کاربری";
            case RESET_PASSWORD -> "کد بازیابی گذرواژه";
            case CHANGE_EMAIL -> "کد تأیید تغییر ایمیل";
            case CHANGE_PHONE -> "کد تأیید تغییر شماره تلفن";
        };
    }
}
