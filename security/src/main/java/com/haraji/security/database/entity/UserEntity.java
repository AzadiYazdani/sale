
package com.haraji.security.database.entity;

import com.haraji.security.constant.RoleType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.haraji.baseinfo.database.entity.BaseEntity;

@Entity
@Table(schema = "sale_db", name = "sec_user")
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private PersonEntity person;

    @Formula("(select ui.value from sale_db.sec_user_identifier ui where ui.user_id = {alias}.id and ui.primary_identifier = true)")
    private String primaryIdentifierValue;

    public Optional<UserIdentifierEntity> getPrimaryIdentifier() {
        return identifiers.stream()
                .filter(UserIdentifierEntity::getPrimaryIdentifier)
                .findFirst();
    }
}
