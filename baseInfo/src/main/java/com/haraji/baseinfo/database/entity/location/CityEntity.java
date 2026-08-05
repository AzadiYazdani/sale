package com.haraji.baseinfo.database.entity.location;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(
        schema = "sale_db",
        name = "city",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_city_province_name",
                        columnNames = {"fk_province", "name"}
                )
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class CityEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "fk_province",
            nullable = false
    )
    private ProvinceEntity province;


    @Column(nullable = false, length = 100)
    private String name;
}
