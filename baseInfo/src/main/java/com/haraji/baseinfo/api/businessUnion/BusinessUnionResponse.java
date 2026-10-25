package com.haraji.baseinfo.api.businessUnion;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "شناسه اتحادیه صنفی", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "شناسه شهر اتحادیه صنفی", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long cityId;

    @Schema(description = "نام اتحادیه صنفی", requiredMode = Schema.RequiredMode.REQUIRED, example = "نبات و آبنبات ریز و قندریز")
    private String title;

    @Schema(description = "رییس اتحادیه صنفی", example = "حسن افخمی علیشاه")
    private String boss;

    @Schema(description = "نشانی اتحادیه صنفی", example = "خیابان مولوی جنب مسجد قنات آباد پلاک ۹۱۳")
    private String address;

    @Schema(description = "تلفن اتحادیه صنفی", example = "۵۵۵۷۸۱۶۵")
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
