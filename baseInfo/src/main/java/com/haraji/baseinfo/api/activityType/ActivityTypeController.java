package com.haraji.baseinfo.api.activityType;

import com.haraji.baseinfo.service.business.ActivityTypeService;
import com.haraji.common.dto.ApiResponse;
import com.haraji.common.logging.LogLevel;
import com.haraji.common.logging.MethodLogging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activitytype")
@Validated
@RequiredArgsConstructor
@MethodLogging(level = LogLevel.INFO)
@Tag(name = "Activity Type", description = "مدیریت انواع فعالیت")
public class ActivityTypeController {

    private final ActivityTypeService activityTypeService;

    @GetMapping
    @Operation(summary = "دریافت تمام انواع فعالیت")
    public ResponseEntity<ApiResponse<List<ActivityTypeResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(activityTypeService.getAll()));
    }

    @GetMapping("/paging")
    @Operation(summary = "دریافت انواع فعالیت به صورت صفحه‌بندی‌شده")
    public ResponseEntity<ApiResponse<Page<ActivityTypeResponse>>> getAllByPaging(
            @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(activityTypeService.getAllByPaging(pageable)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "دریافت نوع فعالیت بر اساس شناسه")
    public ResponseEntity<ApiResponse<ActivityTypeResponse>> getById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(ApiResponse.success(activityTypeService.getById(id)));
    }

    @GetMapping("/search")
    @Operation(summary = "جست‌وجوی انواع فعالیت بر اساس عنوان")
    public ResponseEntity<ApiResponse<List<ActivityTypeResponse>>> search(@RequestParam("title") @NotBlank String title) {
        return ResponseEntity.ok(ApiResponse.success(activityTypeService.searchTitle(title)));
    }
}
