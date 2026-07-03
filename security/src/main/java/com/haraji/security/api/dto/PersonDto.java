package com.haraji.security.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Schema(description = "مدل داده‌ای اطلاعات شخصی")
public class PersonDto implements Serializable {

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

    @Schema(description = "شهر صدور شناسنامه", example = "تهران")
    private String issueCity;

    @Schema(description = "ایمیل", example = "az.ya@yahoo.com")
    private String email;

    @Schema(description = "موبایل", example = "09122935797")
    private String mobile;

    @Schema(description = "پیش شماره شهر", example = "021")
    private String cityCode;

    @Schema(description = "تلفن", example = "77384677")
    private String telephone;

    @Schema(description = "نشانی", example = "بالاتر از فلکه چهارم تهرانپارس")
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
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
