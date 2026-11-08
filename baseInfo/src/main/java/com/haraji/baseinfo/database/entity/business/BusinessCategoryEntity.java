package com.haraji.baseinfo.database.entity.business;

import jakarta.persistence.*;
import lombok.*;

@Entity
// رسته  واحد صنفی
@Table(schema = "sale_db", name = "base_business_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class BusinessCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "business_union_id",
            nullable = false
    )
    private BusinessUnionEntity businessUnion;

    private String title;

}
