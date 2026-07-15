package com.haraji.security.database.entity;

import com.haraji.security.constant.IdentifierType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "sale_db",
        name = "user_identifier",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_identifier",
                        columnNames = {"type", "value"}
                )
        },
        indexes = {
                @Index(name = "idx_identifier_value", columnList = "value")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, builderMethodName = "newInstance")
public class UserIdentifierEntity extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IdentifierType type;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    @Builder.Default
    private Boolean verified = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean primaryIdentifier = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled = true;

}
