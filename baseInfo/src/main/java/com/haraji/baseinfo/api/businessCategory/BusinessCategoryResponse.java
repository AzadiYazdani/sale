
package com.haraji.baseinfo.api.businessCategory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Schema(description = "مدل پاسخ رسته واحد صنفی")
public class BusinessCategoryResponse {

    @Schema(description = "شناسه رسته", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "شناسه اتحادیه", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long businessUnionId;

    @Schema(description = "عنوان رسته", requiredMode = Schema.RequiredMode.REQUIRED, example = "پوشاک زنانه")
    private String title;

    @Override
    public String toString() {
        return "BusinessCategoryResponse{" +
                "id=" + id +
                ", businessUnionId=" + businessUnionId +
                ", title='" + title + '\'' +
                '}';
    }
}
