
package com.haraji.security.database.repository;

import com.haraji.security.constant.OtpPurpose;
import com.haraji.security.database.entity.OtpEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity, Long> {

    Optional<OtpEntity> findTopByIdentifierIdAndPurposeOrderByIdDesc(Long identifierId, OtpPurpose purpose);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT o FROM OtpEntity o
            WHERE o.identifier.id = :identifierId
            AND o.purpose = :purpose
            ORDER BY o.id DESC
            """)
    Optional<OtpEntity> findTopByIdentifierIdAndPurposeAndUsedFalseOrderByIdDesc(@Param("identifierId") Long identifierId, @Param("purpose") OtpPurpose purpose);

    long countByIdentifierIdAndCreatedDateGreaterThanEqualAndCreatedDateLessThan(Long identifierId, LocalDateTime start, LocalDateTime end);
}
