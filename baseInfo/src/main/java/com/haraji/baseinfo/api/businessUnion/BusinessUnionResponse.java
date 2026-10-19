package com.haraji.baseinfo.api.businessUnion;

import jakarta.validation.Valid;
import lombok.*;

// اتحادیه واحد صنفی
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Valid
public class BusinessUnionResponse {
    private Long id;

    private Long cityId;

    private String title;

    private String description;

    @Override
    public String toString() {
        return "BusinessUnion{" +
                "id=" + id +
                ", cityId=" + cityId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
