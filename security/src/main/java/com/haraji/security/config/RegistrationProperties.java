
package com.haraji.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "security.registration")
public class RegistrationProperties {

    private Mode mode = Mode.OTP;

    private List<IdentifierMode> allowedIdentifierModes = new ArrayList<>(List.of(IdentifierMode.EMAIL));

    public boolean isOtpEnabled() {
        return mode == Mode.OTP;
    }

    public enum Mode {
        OTP,
        NO_OTP
    }

    public enum IdentifierMode {
        EMAIL,
        PHONE,
        EMAIL_AND_PHONE
    }
}
