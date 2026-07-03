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
    private String email;
    private String mobile;
    private String cityCode;
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
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
