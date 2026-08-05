package com.haraji.baseinfo.api.businessType;

import com.haraji.baseinfo.mapper.BusinessTypeMapper;
import com.haraji.baseinfo.model.BusinessType;
import com.haraji.baseinfo.service.business.BusinessTypeService;
import com.haraji.common.dto.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
@RequestMapping("/businesstype")
@Slf4j
@Validated
public class BusinessTypeController {

    private final BusinessTypeService businessTypeService;
    private final BusinessTypeMapper businessTypeMapper;

    public BusinessTypeController(BusinessTypeService businessTypeService, BusinessTypeMapper businessTypeMapper) {
        this.businessTypeService = businessTypeService;
        this.businessTypeMapper = businessTypeMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<BusinessTypeResponseDto>>> getAllBusinessTypes() {
        List<BusinessType> businessTypes = businessTypeService.getAll();
        List<BusinessTypeResponseDto> response = businessTypeMapper.toDtoResponseList(businessTypes);
        log.debug("Business types: {}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/all/paging")
    public ResponseEntity<ApiResponse<Page<BusinessTypeResponseDto>>> getBusinessTypesWithPaging(
            @PageableDefault(size = 20, sort = "title", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<BusinessTypeResponseDto> response = businessTypeService.getAllByPaging(pageable).map(businessTypeMapper::toDtoResponse);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{businessTypeId}")
    public ResponseEntity<ApiResponse<BusinessTypeResponseDto>> getById(@PathVariable @Min(1) Long businessTypeId) {
        log.debug("Received businessTypeId={}", businessTypeId);
        BusinessType businessType = businessTypeService.getById(businessTypeId);
        BusinessTypeResponseDto response = businessTypeMapper.toDtoResponse(businessType);
        log.debug("BusinessType response={}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<BusinessTypeResponseDto>>> search(@RequestParam("title") @Valid @NotNull String title) {
        log.debug("received value for searching name is {}", title);
        List<BusinessType> businessTypeList = businessTypeService.searchTitle(title);
        List<BusinessTypeResponseDto> response = businessTypeMapper.toDtoResponseList(businessTypeList);
        log.debug("the list of businessType for sending is {}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
