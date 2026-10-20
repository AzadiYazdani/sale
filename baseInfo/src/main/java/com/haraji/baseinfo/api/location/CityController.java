
package com.haraji.baseinfo.api.location;

import com.haraji.baseinfo.service.location.city.CityService;
import com.haraji.common.dto.ApiResponse;
import com.haraji.common.logging.LogLevel;
import com.haraji.common.logging.MethodLogging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location/provinces")
@Validated
@RequiredArgsConstructor
@MethodLogging(level = LogLevel.INFO)
@Tag(name = "City", description = "مدیریت شهرها")
public class CityController {

    private final CityService cityService;

    @GetMapping(value = "/{provinceId}/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "دریافت همه شهرهای یک استان")
    public ResponseEntity<ApiResponse<List<CityResponse>>> getByProvinceId(
            @PathVariable @Min(1) @Parameter(description = "شناسه استان", example = "1") Long provinceId) {
        return ResponseEntity.ok(ApiResponse.success(cityService.getByProvinceId(provinceId)));
    }
}
