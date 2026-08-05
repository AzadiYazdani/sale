package com.haraji.baseinfo.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Valid
public class BusinessType {
    private Long id;

    private Long parentId;

    @NotBlank
    private String title;

    @Override
    public String toString() {
        return "activityType{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
