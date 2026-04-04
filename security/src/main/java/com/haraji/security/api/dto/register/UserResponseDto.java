package com.haraji.security.api.dto.register;

import com.haraji.security.api.dto.PersonDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
public class UserResponseDto implements Serializable {

//    @ApiModelProperty(value = "شناسه کاربر", dataType = "long", example = "1")
//    private long id;

    @ApiModelProperty(value = "نام کاربری", dataType = "String", example = "\"azadi.yazdani\"")
    private String username;

    @ApiModelProperty(value = "ایمیل", dataType = "String", example = "\"azadi.yazdani@yahoo.com\"")
    private String email;

    @ApiModelProperty(value = "زمان ساخته شدن", dataType = "String", example = "\"1/1/1403\"")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "زمان پایان اعتبار", dataType = "String", example = "\"1/10/1403\"")
    private LocalDateTime expireTime;

    private PersonDto person;

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                ", expireTime=" + expireTime +
                ", person=" + person +
                '}';
    }
}
