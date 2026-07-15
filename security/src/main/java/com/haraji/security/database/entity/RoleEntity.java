package com.haraji.security.database.entity;

import com.haraji.security.constant.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "sale_db",name = "role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class RoleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private RoleType role;

    @OneToMany(mappedBy = "role")
    @Builder.Default
    private List<UserEntity> users = new ArrayList<>();

}
