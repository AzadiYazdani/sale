package com.haraji.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "security.otp.channels")
public class OtpChannelProperties {

    private Channel email = new Channel();
    private Channel phone = new Channel();

    @Getter
    @Setter
    public static class Channel {
        private boolean enabled;
    }
}
