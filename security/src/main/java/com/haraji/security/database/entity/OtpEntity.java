package com.haraji.security.database.entity;

import com.haraji.security.constant.OtpPurpose;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(schema = "sale_db",
        name = "otp",
        indexes = {
                @Index(name = "idx_otp_code", columnList = "code"),
                @Index(name = "idx_otp_expire", columnList = "expire_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtpEntity extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "identifier_id")
    private UserIdentifierEntity identifier;

    @Column(nullable = false, length = 10)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OtpPurpose purpose;

    @Column(nullable = false)
    private LocalDateTime expireAt;

    @Column(nullable = false)
    private boolean used = false;

}
