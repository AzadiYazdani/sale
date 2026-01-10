package com.haraji.security.database.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Entity
@Table(schema = "sale_db", name = "person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class PersonEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name="first_name")
    private String firstName;

    @Column (name="last_name")
    private String lastName;

    @Column (name="father_name")
    private String fatherName;

    @Column (name="national_code")
    private String nationalCode;

    @Column (name="identity_number")
    private String identityNumber;

    @Column (name="date_of_birth")
    private LocalDate birthDate;

    @Column (name="city_of_birth")
    private String birthCity;

    @Column (name="city_of_issue")
    private String issueCity;

    private String telephone;

    private String address;


}
