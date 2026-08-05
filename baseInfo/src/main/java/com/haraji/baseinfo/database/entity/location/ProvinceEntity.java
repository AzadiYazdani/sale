package com.haraji.baseinfo.database.entity.location;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(schema = "sale_db",name = "province")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class ProvinceEntity {

    @Id
    private Long id;

    @JoinColumn(name = "name")
    private String name;

    @OneToMany(mappedBy = "province", fetch = FetchType.LAZY)
    private List<CityEntity> cities;

}
