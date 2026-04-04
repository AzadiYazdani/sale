package com.haraji.security.api.dto.register;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class UserRequestDto  implements Serializable {

    @ApiModelProperty(value = "نام کاربری", dataType = "String", required = true, example = "\"azadi.yazdani\"")
    private String username;

    @ApiModelProperty(value = "گذرواژه", dataType = "String", required = true, example = "\"Idaza123\"")
    private String password;

    @ApiModelProperty(value = "ایمیل", dataType = "String", required = true, example = "\"azadi.yazdani@yahoo.com\"")
    private String email;

    @ApiModelProperty(value = "تلفن", dataType = "String", example = "\"09122935797\"")
    private String telephone;

    @ApiModelProperty(value = "نشانی", dataType = "String", example = "\"بالاتر از فلکه چهارم تهرانپارس\"")
    private String address;

    @ApiModelProperty(value = "نام کوچک", dataType = "String", example = "\"آزادی\"")
    private String firstName;

    @ApiModelProperty(value = "نام خانوادگی", dataType = "String", example = "\"یزدانی\"")
    private String lastName;

    @ApiModelProperty(value = "نام پدر", dataType = "String", example = "\"حسن\"")
    private String fatherName;

    @ApiModelProperty(value = "کد ملی", dataType = "String", example = "\"0451238680\"")
    private String nationalCode;

    @ApiModelProperty(value = "شماره شناسنامه", dataType = "String",  example = "\"1123\"")
    private String identityNumber;

    @ApiModelProperty(value = "تاریخ تولد", dataType = "String", example = "\"1/1/1358\"")
    private String birthDate;

    @ApiModelProperty(value = "شهر تولد", dataType = "String", example = "\"تهران\"")
    private String birthCity;

    @Override
    public String toString() {
        return "UserRequestDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", identityNumber='" + identityNumber + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", birthCity='" + birthCity + '\'' +
                '}';
    }
}
