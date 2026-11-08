
package com.haraji.security.dto.user;

import com.haraji.security.constant.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Schema(description = "درخواست تغییر نقش و وضعیت دسترسی کاربر")
public class UserAccessUpdateRequest implements Serializable {

    @Schema(description = "نقش جدید کاربر", example = "VIEWER")
    private RoleType role;

    @Schema(description = "فعال بودن حساب", example = "true")
    private Boolean enabled;

    @Schema(description = "مسدود بودن حساب", example = "false")
    private Boolean locked;
}
