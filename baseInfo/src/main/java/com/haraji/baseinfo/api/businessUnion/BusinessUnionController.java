
package com.haraji.baseinfo.api.businessUnion;

import com.haraji.baseinfo.service.business.businessunion.BusinessUnionService;
import com.haraji.common.dto.ApiResponse;
import com.haraji.common.logging.LogLevel;
import com.haraji.common.logging.MethodLogging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/businessunion")
@Validated
@RequiredArgsConstructor
@MethodLogging(level = LogLevel.INFO)
@Tag(name = "Business Union", description = "مدیریت اتحادیه‌های صنفی")
public class BusinessUnionController {

    private final BusinessUnionService businessUnionService;

    @GetMapping
    @Operation(summary = "دریافت تمام اتحادیه‌های صنفی")
    public ResponseEntity<ApiResponse<List<BusinessUnionResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(businessUnionService.getAll()));
    }

    @GetMapping("/paging")
    @Operation(summary = "دریافت اتحادیه‌های صنفی به صورت صفحه‌بندی‌شده")
    public ResponseEntity<ApiResponse<Page<BusinessUnionResponse>>> getAllByPaging(
            @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(businessUnionService.getAllByPaging(pageable)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "دریافت اتحادیه صنفی بر اساس شناسه")
    public ResponseEntity<ApiResponse<BusinessUnionResponse>> getById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(ApiResponse.success(businessUnionService.getById(id)));
    }

    @GetMapping("/city/{cityId}")
    @Operation(summary = "دریافت اتحادیه‌های صنفی یک شهر")
    public ResponseEntity<ApiResponse<List<BusinessUnionResponse>>> getByCityId(@PathVariable @Min(1) Long cityId) {
        return ResponseEntity.ok(ApiResponse.success(businessUnionService.getByCityId(cityId)));
    }

    @GetMapping("/city/{cityId}/paging")
    @Operation(summary = "دریافت اتحادیه‌های صنفی یک شهر به صورت صفحه‌بندی‌شده")
    public ResponseEntity<ApiResponse<Page<BusinessUnionResponse>>> getByCityIdPaging(
            @PathVariable @Min(1) Long cityId,
            @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(businessUnionService.getByCityIdPaging(cityId, pageable)));
    }

    @GetMapping("/search")
    @Operation(summary = "جست‌وجوی اتحادیه صنفی بر اساس عنوان")
    public ResponseEntity<ApiResponse<List<BusinessUnionResponse>>> search(@RequestParam("title") @NotBlank String title) {
        return ResponseEntity.ok(ApiResponse.success(businessUnionService.searchTitle(title)));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "ثبت اتحادیه صنفی")
    public ResponseEntity<ApiResponse<BusinessUnionResponse>> create(@Valid @RequestBody BusinessUnionRequest request) {
        return ResponseEntity.ok(ApiResponse.success(businessUnionService.create(request)));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "ویرایش اتحادیه صنفی")
    public ResponseEntity<ApiResponse<BusinessUnionResponse>> update(@PathVariable @Min(1) Long id, @Valid @RequestBody BusinessUnionRequest request) {
        return ResponseEntity.ok(ApiResponse.success(businessUnionService.update(id, request)));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "حذف اتحادیه صنفی")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable @Min(1) Long id) {
        businessUnionService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
