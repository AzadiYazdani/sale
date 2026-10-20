
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

    @Size(max = 1000, message = "توضیحات اتحادیه نباید بیشتر از ۱۰۰۰ کاراکتر باشد.")
    private String description;
}
