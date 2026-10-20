
package com.haraji.baseinfo.api.location;

import com.haraji.baseinfo.service.location.province.ProvinceService;
import com.haraji.common.dto.ApiResponse;
import com.haraji.common.logging.LogLevel;
import com.haraji.common.logging.MethodLogging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@Tag(name = "Province", description = "مدیریت استان‌ها")
public class ProvinceController {

    private final ProvinceService provinceService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "دریافت همه استان‌ها")
    public ResponseEntity<ApiResponse<List<ProvinceResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(provinceService.getAll()));
    }

    @GetMapping(value = "/paging", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "دریافت استان‌ها به صورت صفحه‌بندی‌شده")
    public ResponseEntity<ApiResponse<Page<ProvinceResponse>>> getAllByPaging(
            @RequestParam(defaultValue = "0") @Min(0) @Parameter(description = "شماره صفحه", example = "0") int pageNo,
            @RequestParam(defaultValue = "20") @Min(1) @Parameter(description = "تعداد اقلام در هر صفحه", example = "20") int pageSize,
            @RequestParam(required = false) @Parameter(description = "نام فیلد مرتب‌سازی", example = "name") String sortName,
            @RequestParam(required = false) @Parameter(description = "جهت مرتب‌سازی", example = "DESC") String direction) {

        String sortColumn = sortName == null || sortName.isBlank() ? "name" : sortName;
        Sort.Direction sortDirection = "ASC".equalsIgnoreCase(direction) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortDirection, sortColumn));

        return ResponseEntity.ok(ApiResponse.success(provinceService.getAllByPaging(pageable)));
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "جست‌وجوی استان بر اساس نام")
    public ResponseEntity<ApiResponse<List<ProvinceResponse>>> search(
            @RequestParam("name") @NotBlank @Parameter(description = "بخشی از نام استان", example = "آذر") String name) {
        return ResponseEntity.ok(ApiResponse.success(provinceService.searchTitle(name)));
    }

    @GetMapping(value = "/{provinceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "دریافت استان بر اساس شناسه")
    public ResponseEntity<ApiResponse<ProvinceResponse>> getById(
            @PathVariable @Min(1) @Parameter(description = "شناسه استان", example = "1") Long provinceId) {
        return ResponseEntity.ok(ApiResponse.success(provinceService.getById(provinceId)));
    }
}
