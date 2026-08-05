package com.haraji.baseinfo.database.entity.business;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "sale_db", name = "activity_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class ActivityTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

}
