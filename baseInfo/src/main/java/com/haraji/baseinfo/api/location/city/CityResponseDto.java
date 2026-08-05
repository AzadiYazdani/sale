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
    private Long id;

    @Schema(description = "شناسه استان", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long provinceId;

    @Schema(description = "نام شهر", requiredMode = Schema.RequiredMode.REQUIRED, example = "\"ابهر\"")
    private String name;

    @Override
    public String toString() {
        return "CityResponseDto{" +
                "id=" + id +
                ", provinceId=" + provinceId +
                ", name='" + name + '\'' +
                '}';
    }
}
