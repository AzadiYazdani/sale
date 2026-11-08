package com.haraji.baseinfo.database.entity.business;

import com.haraji.baseinfo.database.entity.location.CityEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
// اتحادیه واحد صنفی
@Table(schema = "sale_db", name = "base_business_union")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true)
public class BusinessUnionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "city_id",
            nullable = false
    )
    private CityEntity city;

    private String title;

    private String boss;

    private String address;

    private String telephone;

    @OneToMany(mappedBy = "businessUnion", fetch = FetchType.LAZY)
    private List<BusinessCategoryEntity> businessCategoryEntities;

}
