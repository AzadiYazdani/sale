package com.haraji.baseinfo.database.entity.business;

import jakarta.persistence.*;
import lombok.*;

@Entity
// اتحادیه واحد صنفی
@Table(schema = "sale_db", name = "base_business_union")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class BusinessUnionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city_id")
    private Long cityId;

    private String title;

    private String boss;

    private String address;

    private String telephone;

}
