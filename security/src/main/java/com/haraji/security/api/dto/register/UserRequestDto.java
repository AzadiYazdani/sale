package com.haraji.security.api.dto.register;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Schema(description = "مدل درخواست ثبت‌نام کاربر")
public class UserRequestDto implements Serializable {

    @Schema(description = "نام کاربری", requiredMode = Schema.RequiredMode.REQUIRED, example = "azadi.yazdani")
    private String username;

    @Schema(description = "گذرواژه", requiredMode = Schema.RequiredMode.REQUIRED, example = "Idaza123")
    private String password;

    @Schema(description = "ایمیل", requiredMode = Schema.RequiredMode.REQUIRED, example = "azadi.yazdani@yahoo.com")
    private String email;

    @Schema(description = "تلفن", example = "09122935797")
    private String telephone;

    @Schema(description = "نشانی", example = "بالاتر از فلکه چهارم تهرانپارس")
    private String address;

    @Schema(description = "نام کوچک", example = "آزادی")
    private String firstName;

    @Schema(description = "نام خانوادگی", example = "یزدانی")
    private String lastName;

    @Schema(description = "نام پدر", example = "حسن")
    private String fatherName;

    @Schema(description = "کد ملی", example = "0451238680")
    private String nationalCode;

    @Schema(description = "شماره شناسنامه", example = "1123")
    private String identityNumber;

    @Schema(description = "تاریخ تولد", example = "1/1/1358")
    private String birthDate;

    @Schema(description = "شهر تولد", example = "تهران")
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
