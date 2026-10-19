package com.haraji.baseinfo.api.businessUnion;

import com.haraji.baseinfo.service.business.BusinessUnionService;
import com.haraji.common.dto.ApiResponse;
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
@RequestMapping("/businessunion")
@Slf4j
@Validated
public class BusinessUnionController {

    private final BusinessUnionService businessUnionService;

    public BusinessUnionController(BusinessUnionService businessUnionService) {
        this.businessUnionService = businessUnionService;
    }


    @GetMapping
    @Operation(summary = "دریافت تمام اتحادیه های صنفی")
    public ResponseEntity<ApiResponse<List<BusinessUnionResponse>>> getAll() {
        List<BusinessUnionResponse> response = businessUnionService.getAll();
        log.debug("Business types: {}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/paging")
    public ResponseEntity<ApiResponse<Page<BusinessUnionResponse>>> getActivityTypesWithPaging(
            @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.DESC)  Pageable pageable) {

        Page<BusinessUnionResponse> response = businessUnionService.getAllByPaging(pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @Operation(summary = "دریافت اتحادیه صنفی بر اساس شناسه")
    public ResponseEntity<ApiResponse<BusinessUnionResponse>> getById(@PathVariable @Min(1) Long id) {
        log.debug("Received businessTypeId={}", id);
        BusinessUnionResponse response = businessUnionService.getById(id);
        log.debug("ActivityType response={}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/search")
    @Operation(summary = "جست‌وجوی اتحادیه صنفی بر اساس عنوان")
    public ResponseEntity<ApiResponse<List<BusinessUnionResponse>>> search(@RequestParam("title") @NotBlank String title) {
        log.debug("Received title for searching: {}", title);
        List<BusinessUnionResponse> response = businessUnionService.searchTitle(title);
        log.debug("Activity types returned: {}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
