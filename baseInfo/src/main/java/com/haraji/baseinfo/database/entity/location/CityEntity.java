package com.haraji.baseinfo.database.entity.location;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Table(schema = "sale_db", name = "city")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class CityEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "fk_state")
    private StateEntity state;

    private String title;
}
