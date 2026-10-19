package com.haraji.security.dto.register;

import com.haraji.security.dto.user.PersonDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Schema(description = "مدل پاسخ اطلاعات کاربر")
public class UserResponse implements Serializable {

    @Schema(description = "نام کاربری", example = "azadi.yazdani")
    private String username;

    @Schema(description = "زمان ساخته شدن", example = "2024-03-20T10:00:00")
    private LocalDateTime createTime;

    @Schema(description = "اطلاعات شخصی کاربر")
    private PersonDto person;

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "username='" + username + '\'' +
                ", createTime=" + createTime +
                ", person=" + person +
                '}';
    }
}
