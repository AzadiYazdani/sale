package com.haraji.security.database.entity;

import com.haraji.security.constant.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(schema = "sale_db", name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true, builderMethodName = "newInstance")
public class UserEntity extends BaseEntity<Long> implements Serializable {

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private RoleType role = RoleType.VIEWER;

    @Column(nullable = false)
    @Builder.Default
    private Boolean enabled = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean locked = false;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<UserIdentifierEntity> identifiers = new ArrayList<>();

    @OneToOne
    private PersonEntity person;

    public Optional<UserIdentifierEntity> getPrimaryIdentifier() {
        return identifiers.stream()
                .filter(UserIdentifierEntity::getPrimaryIdentifier)
                .findFirst();

    }
}
