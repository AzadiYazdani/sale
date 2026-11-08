package com.haraji.security.dto.user;

import com.haraji.security.constant.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "اطلاعات کاربر جهت نمایش در پنل مدیریت")
public class AdminUserResponse {

    @Schema(description = "شناسه یکتای کاربر", example = "15")
    private Long id;

    @Schema(description = "شناسه ورود کاربر (نام کاربری، ایمیل یا شماره موبایل)", example = "user@example.com")
    private String identifier;

    @Schema(description = "نقش کاربر در سامانه", example = "ADMIN", implementation = RoleType.class)
    private RoleType role;

    @Schema(description = "وضعیت فعال بودن حساب کاربری", example = "true")
    private Boolean enabled;

    @Schema(description = "وضعیت مسدود بودن حساب کاربری", example = "false")
    private Boolean locked;

    @Schema(description = "تاریخ و زمان ایجاد حساب کاربری به شمسی", example = "1405/08/09 10:30:00")
    private String createdDate;

    @Schema(description = "اطلاعات شخصی کاربر")
    private PersonResponse person;
}
