package com.haraji.baseinfo.model.business;

import jakarta.validation.Valid;
import lombok.*;

// رسته  واحد صنفی
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Valid
public class BusinessCategory {
    private Long id;

    private Long parentId;

    private String title;

    private String description;

    @Override
    public String toString() {
        return "BusinessCategory{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
