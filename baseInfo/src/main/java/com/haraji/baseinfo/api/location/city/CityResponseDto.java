package com.haraji.baseinfo.api.location.city;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Schema(description = "مدل پاسخ اطلاعات شهر")
public class CityResponseDto {

    @Schema(description = "شناسه شهر", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private int id;

    @Schema(description = "شناسه استان", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private int stateId;

    @Schema(description = "نام شهر", requiredMode = Schema.RequiredMode.REQUIRED, example = "\"ابهر\"")
    private String title;

    @Override
    public String toString() {
        return "CityResponseDto{" +
                "id=" + id +
                ", stateId=" + stateId +
                ", title='" + title + '\'' +
                '}';
    }
}
