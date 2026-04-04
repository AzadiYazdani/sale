package com.haraji.baseinfo.database.entity.location;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(schema = "sale_db",name = "state")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "title")
    private String title;

    @OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
    private List<CityEntity> cities;

}
