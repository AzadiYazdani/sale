package com.haraji.baseinfo.api.activityType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.*;

// نوع فعالیت کسب و کار
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Valid
@Schema(description = "مدل پاسخ نوع فعالیت کسب و کار")
public class ActivityTypeResponse {

    @Schema(description = "شناسه نوع فعالیت کسب و کار", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "نوع فعالیت کسب و کار", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String title;

    private String description;

    @Override
    public String toString() {
        return "ActivityType{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
