package com.haraji.security.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class PersonDto implements Serializable {

    @ApiModelProperty(value = "نام کوچک", dataType = "String", example = "\"آزادی\"")
    private String firstName;

    @ApiModelProperty(value = "نام خانوادگی", dataType = "String", example = "\"یزدانی\"")
    private String lastName;

    @ApiModelProperty(value = "نام پدر", dataType = "String", example = "\"حسن\"")
    private String fatherName;

    @ApiModelProperty(value = "کد ملی", dataType = "String", example = "\"0451238680\"")
    private String nationalCode;

    @ApiModelProperty(value = "شماره شناسنامه", dataType = "String", example = "\"1123\"")
    private String identityNumber;

    @ApiModelProperty(value = "تاریخ تولد", dataType = "String", example = "\"1/1/1358\"")
    private String birthDate;

    @ApiModelProperty(value = "شهر تولد", dataType = "String", example = "\"تهران\"")
    private String birthCity;

    @ApiModelProperty(value = "شهر صدور شناسنامه", dataType = "String", example = "\"azadi.yazdani@yahoo.com\"")
    private String issueCity;

    @ApiModelProperty(value = "تلفن", dataType = "String", example = "\"09122935797\"")
    private String telephone;

    @ApiModelProperty(value = "نشانی", dataType = "String", example = "\"بالاتر از فلکه چهارم تهرانپارس\"")
    private String address;

    @Override
    public String toString() {
        return "PersonDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", identityNumber='" + identityNumber + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", birthCity='" + birthCity + '\'' +
                ", issueCity='" + issueCity + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
