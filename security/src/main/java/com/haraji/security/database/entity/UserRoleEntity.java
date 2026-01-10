package com.haraji.security.database.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(schema = "sale_db",name = "user_role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class UserRoleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToMany
//
//    private UserEntity user_id;
//
//    @ManyToMany
//    private RoleEntity role_id;

}
