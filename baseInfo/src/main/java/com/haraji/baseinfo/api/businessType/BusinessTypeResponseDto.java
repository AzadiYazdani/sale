package com.haraji.baseinfo.api.businessType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Schema(description = "مدل پاسخ نوع کسب و کار")
public class BusinessTypeResponseDto {

    @Schema(description = "شناسه کسب و کار", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private long id;

    @Schema(description = "نوع کسب و کار", requiredMode = Schema.RequiredMode.REQUIRED, example = "\"پوشاک زنانه\"")
    private String title;

    @Override
    public String toString() {
        return "BusinessTypeResponseDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
