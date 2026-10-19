package com.haraji.baseinfo.api.activityType;

import jakarta.validation.Valid;
import lombok.*;

// نوع فعالیت کسب و کار
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Valid
public class ActivityTypeResponse {
    private Long id;

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
