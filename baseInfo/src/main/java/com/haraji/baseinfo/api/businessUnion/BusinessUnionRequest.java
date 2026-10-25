
package com.haraji.baseinfo.api.businessUnion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessUnionRequest {

    @NotNull(message = "شناسه شهر الزامی است.")
    @Min(value = 1, message = "شناسه شهر باید بزرگ‌تر از صفر باشد.")
    private Long cityId;

    @NotBlank(message = "عنوان اتحادیه الزامی است.")
    @Size(max = 200, message = "عنوان اتحادیه نباید بیشتر از ۲۰۰ کاراکتر باشد.")
    private String title;

    @Size(max = 100, message = "نام رییس اتحادیه نباید بیشتر از ۱۰۰ کاراکتر باشد.")
    private String boss;

    @Size(max = 100, message = "نشانی اتحادیه نباید بیشتر از ۱۰۰ کاراکتر باشد.")
    private String address;

    @Size(max = 50, message = "شماره تلفن اتحادیه نباید بیشتر از ۱۰۰ کاراکتر باشد.")
    private String telephone;
}
