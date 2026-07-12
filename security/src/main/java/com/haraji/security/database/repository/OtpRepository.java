package com.haraji.security.database.repository;

import com.haraji.security.constant.OtpPurpose;
import com.haraji.security.database.entity.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity,Long> {

    Optional<OtpEntity> findTopByIdentifierIdAndPurposeAndUsedFalseOrderByIdDesc(
            Long identifierId,
            OtpPurpose purpose
    );

    Optional<OtpEntity> findTopByIdentifierIdAndPurposeAndUsedFalseOrderByCreatedDateDesc(
            Long identifierId,
            OtpPurpose purpose
    );

    void deleteByExpireAtBefore(LocalDateTime time);
    void deleteAllByIdentifierId(Long identifierId);

}
