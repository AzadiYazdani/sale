package com.haraji.security.database.entity;

import com.haraji.security.constant.RoleType;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(schema = "sale_db",name = "user_history")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class UserHistoryEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Column (name="create_time")
    private LocalDateTime createTime;

    @Column (name="expire_time")
    private LocalDateTime expireTime;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="role")
    private RoleType role;

    @OneToOne
    private PersonEntity person;

}
