package com.haraji.baseinfo.api.activityType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityTypeRequest {

    @NotBlank(message = "نام نوع فعالیت الزامی است.")
    @Size(max = 100, message = "نام نوع فعالیت نباید بیشتر از ۱۰۰ کاراکتر باشد.")
    private String name;

    @NotBlank(message = "عنوان نوع فعالیت الزامی است.")
    @Size(max = 200, message = "عنوان نوع فعالیت نباید بیشتر از ۲۰۰ کاراکتر باشد.")
    private String title;
}
