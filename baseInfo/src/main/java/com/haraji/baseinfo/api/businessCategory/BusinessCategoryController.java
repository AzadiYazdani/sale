
package com.haraji.baseinfo.api.businessCategory;

import com.haraji.baseinfo.service.business.businesscategory.BusinessCategoryService;
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
@RequestMapping("/businesscategory")
@Validated
@RequiredArgsConstructor
@MethodLogging(level = LogLevel.INFO)
@Tag(name = "Business Category", description = "مدیریت رسته‌های واحد صنفی")
public class BusinessCategoryController {

    private final BusinessCategoryService businessCategoryService;

    @GetMapping
    @Operation(summary = "دریافت تمام رسته‌های واحد صنفی")
    public ResponseEntity<ApiResponse<List<BusinessCategoryResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(businessCategoryService.getAll()));
    }

    @GetMapping("/paging")
    @Operation(summary = "دریافت رسته‌های واحد صنفی به صورت صفحه‌بندی‌شده")
    public ResponseEntity<ApiResponse<Page<BusinessCategoryResponse>>> getAllByPaging(
            @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(businessCategoryService.getAllByPaging(pageable)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "دریافت رسته واحد صنفی بر اساس شناسه")
    public ResponseEntity<ApiResponse<BusinessCategoryResponse>> getById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(ApiResponse.success(businessCategoryService.getById(id)));
    }

    @GetMapping("/parent/{parentId}")
    @Operation(summary = "دریافت زیرمجموعه‌های یک رسته")
    public ResponseEntity<ApiResponse<List<BusinessCategoryResponse>>> getByParentId(@PathVariable @Min(1) Long parentId) {
        return ResponseEntity.ok(ApiResponse.success(businessCategoryService.getByParentId(parentId)));
    }

    @GetMapping("/search")
    @Operation(summary = "جست‌وجوی رسته بر اساس عنوان")
    public ResponseEntity<ApiResponse<List<BusinessCategoryResponse>>> search(@RequestParam("title") @NotBlank String title) {
        return ResponseEntity.ok(ApiResponse.success(businessCategoryService.searchTitle(title)));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "ثبت رسته جدید")
    public ResponseEntity<ApiResponse<BusinessCategoryResponse>> create(@Valid @RequestBody BusinessCategoryRequest request) {
        return ResponseEntity.ok(ApiResponse.success(businessCategoryService.create(request)));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "ویرایش رسته")
    public ResponseEntity<ApiResponse<BusinessCategoryResponse>> update(@PathVariable @Min(1) Long id, @Valid @RequestBody BusinessCategoryRequest request) {
        return ResponseEntity.ok(ApiResponse.success(businessCategoryService.update(id, request)));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "حذف رسته")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable @Min(1) Long id) {
        businessCategoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
