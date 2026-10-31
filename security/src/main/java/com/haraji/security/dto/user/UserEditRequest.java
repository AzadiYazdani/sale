
package com.haraji.security.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Schema(description = "مدل درخواست ویرایش اطلاعات کاربر")
public class UserEditRequest implements Serializable {

    @Schema(description = "ایمیل", example = "azadi.yazdani@yahoo.com")
    private String email;

    @Schema(description = "تلفن همراه", example = "09121234567")
    private String mobile;

    @Schema(description = "نشانی", example = "بالاتر از فلکه چهارم تهرانپارس")
    private String address;

    @Schema(description = "نام کوچک", example = "آزادی")
    private String firstName;

    @Schema(description = "نام خانوادگی", example = "یزدانی")
    private String lastName;

    @Schema(description = "نام پدر", example = "حسن")
    private String fatherName;

    @Schema(description = "کد ملی", example = "0451231234")
    private String nationalCode;

    @Schema(description = "شماره شناسنامه", example = "1123")
    private String identityNumber;

    @Schema(description = "تاریخ تولد شمسی", example = "1378/01/01")
    private String birthDate;

    @Schema(description = "شهر تولد", example = "تهران")
    private String birthCity;

    @Override
    public String toString() {
        return "UserEditRequest{" +
                "email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", birthCity='" + birthCity + '\'' +
                '}';
    }
}
