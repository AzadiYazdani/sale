package com.haraji.baseinfo.database.entity.business;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(schema = "sale_db", name = "business_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class BusinessTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_Id")
    private Long parentId;

    private String title;

}
