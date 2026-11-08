package com.haraji.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", toBuilder = true, builderMethodName = "newInstance")
@Schema(description = "مدل درخواست صفحه‌بندی")
public class PageRequestDto implements Serializable {

    @Schema(description = "شماره صفحه", example = "1")
    private Integer page;

    @Schema(description = "شمارگان اقلام هر صفحه", example = "10")
    private Integer size;

}
