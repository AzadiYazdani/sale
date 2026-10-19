package com.haraji.baseinfo.api.activityType;

import com.haraji.baseinfo.service.business.ActivityTypeService;
import com.haraji.common.dto.ApiResponse;
import com.haraji.common.logging.LogLevel;
import com.haraji.common.logging.MethodLogging;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Validated
@MethodLogging(level = LogLevel.INFO)
public class ActivityTypeController {

    private final ActivityTypeService activityTypeService;

    public ActivityTypeController(ActivityTypeService activityTypeService) {
        this.activityTypeService = activityTypeService;
    }

    @GetMapping
    @Operation(summary = "دریافت تمام انواع فعالیت")
    public ResponseEntity<ApiResponse<List<ActivityTypeResponse>>> getAll() {
        log.debug("ActivityTypeController.getAll received");
        List<ActivityTypeResponse> response = activityTypeService.getAll();
        log.debug("ActivityTypeController.getAll response: {}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/paging")
    public ResponseEntity<ApiResponse<Page<ActivityTypeResponse>>> getActivityTypesWithPaging(
            @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.DESC)  Pageable pageable) {
        log.debug("ActivityTypeController.getActivityTypesWithPaging received");
        Page<ActivityTypeResponse> response = activityTypeService.getAllByPaging(pageable);
        log.debug("ActivityTypeController.getActivityTypesWithPaging response={}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "دریافت نوع فعالیت بر اساس شناسه")
    public ResponseEntity<ApiResponse<ActivityTypeResponse>> getById(@PathVariable @Min(1) Long id) {
        log.debug("ActivityTypeController.getById: businessTypeId={}", id);
        ActivityTypeResponse response = activityTypeService.getById(id);
        log.debug("ActivityTypeController.getById: response={}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ActivityTypeResponse>>> search(@RequestParam("title") @NotBlank String title) {
        log.debug("ActivityTypeController.search: title={}", title);
        List<ActivityTypeResponse> response = activityTypeService.searchTitle(title);
        log.debug("ActivityTypeController.search: response={}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
