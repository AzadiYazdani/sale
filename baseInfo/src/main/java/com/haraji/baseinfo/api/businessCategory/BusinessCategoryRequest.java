
package com.haraji.baseinfo.api.businessCategory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessCategoryRequest {

    @NotNull(message = "شناسه رسته الزامی است.")
    @Min(value = 1, message = "شناسه رسته باید بزرگ‌تر از صفر باشد.")
    private Long businessCategoryId;

    @NotBlank(message = "عنوان رسته الزامی است.")
    @Size(max = 200, message = "عنوان رسته نباید بیشتر از ۲۰۰ کاراکتر باشد.")
    private String title;
}
