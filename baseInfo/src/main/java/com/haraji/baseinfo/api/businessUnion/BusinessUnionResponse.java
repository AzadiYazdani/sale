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

    private String boss;

    private String address;

    private String telephone;

    @Override
    public String toString() {
        return "BusinessUnionResponse{" +
                "id=" + id +
                ", cityId=" + cityId +
                ", title='" + title + '\'' +
                ", boss='" + boss + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
