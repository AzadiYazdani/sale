package com.haraji.security.model;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class Person{

    private String firstName;
    private String lastName;
    private String fatherName;
    private String nationalCode;
    private String identityNumber;
    private LocalDate birthDate;
    private String birthCity;
    private String issueCity;
    private String telephone;
    private String address;

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", identityNumber='" + identityNumber + '\'' +
                ", birthDate=" + birthDate +
                ", birthCity='" + birthCity + '\'' +
                ", issueCity='" + issueCity + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
