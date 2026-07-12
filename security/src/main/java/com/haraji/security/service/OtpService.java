package com.haraji.security.service;

import com.haraji.security.constant.OtpPurpose;
import com.haraji.security.database.entity.OtpEntity;
import com.haraji.security.database.entity.UserIdentifierEntity;

import java.util.Optional;

public interface OtpService {

    Optional<OtpEntity> getLastOtp(
            Long identifierId,
            OtpPurpose purpose
    );

    void generateOtp(UserIdentifierEntity identifier, OtpPurpose purpose);

    void verifyOtp(UserIdentifierEntity identifier,
                   OtpPurpose purpose,
                   String code);



}
