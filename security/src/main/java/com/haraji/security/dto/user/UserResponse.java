package com.haraji.security.dto.user;

import com.haraji.security.constant.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "اطلاعات کاربر جهت نمایش در سامانه")
public class UserResponse {

    @Schema(description = "شناسه ورود کاربر (نام کاربری، ایمیل یا شماره موبایل)", example = "user@example.com")
    private String identifier;

    @Schema(description = "نقش کاربر در سامانه", example = "VIEWER", implementation = RoleType.class)
    private RoleType role;

    @Schema(description = "وضعیت فعال بودن حساب کاربری", example = "true")
    private Boolean enabled;

    @Schema(description = "تاریخ و ساعت ایجاد کاربر به شمسی", example = "1405/08/09 10:30:00")
    private String createdDate;

    @Schema(description = "اطلاعات شخصی کاربر")
    private PersonDto person;
}
