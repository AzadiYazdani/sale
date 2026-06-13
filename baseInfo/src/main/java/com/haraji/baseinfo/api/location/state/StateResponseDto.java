package com.haraji.baseinfo.api.location.state;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Schema(description = "مدل پاسخ اطلاعات استان")
public class StateResponseDto implements Serializable {

    @Schema(description = "شناسه استان", example = "1")
    private int id;

    @Schema(description = "نام استان", example = "\"آذربایجان شرقی\"")
    private String title;

    @Override
    public String toString() {
        return "StateResponseDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
