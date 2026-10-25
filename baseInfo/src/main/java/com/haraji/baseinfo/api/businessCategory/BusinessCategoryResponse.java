package com.haraji.baseinfo.api.businessCategory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Schema(description = "مدل پاسخ رسته  واحد صنفی (رسته شغلی)")
public class BusinessCategoryResponse {

    @Schema(description = "شناسه رسته  واحد صنفی (رسته شغلی)", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "زیرمجموعه", requiredMode = Schema.RequiredMode.REQUIRED, example = "\"پوشاک\"")
    private Long parentId;

    @Schema(description = "رسته  واحد صنفی (رسته شغلی)", requiredMode = Schema.RequiredMode.REQUIRED, example = "\"پوشاک زنانه\"")
    private String title;

    @Override
    public String toString() {
        return "BusinessTypeResponseDto{" +
                "id=" + id +
                ", parentId='" + parentId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

}
