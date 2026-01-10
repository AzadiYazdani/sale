package com.haraji.baseinfo.api.businessType;


import com.haraji.baseinfo.mapper.BusinessTypeMapper;
import com.haraji.baseinfo.model.BusinessType;
import com.haraji.baseinfo.service.business.BusinessTypeService;
import com.haraji.common.dto.ResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Api(value = "Business Type operations")
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

    @GetMapping(value = "/all")
    @ApiOperation(value = "یافتن همه انواع کسب و کار", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<BusinessTypeResponseDto>> getAllBusinessTypes() {
        List<BusinessType> businessType = businessTypeService.getAll();
        List<BusinessTypeResponseDto> lstDtoResponse = businessTypeMapper.toDtoResponseList(businessType);
        log.debug("the BusinessTypeDto for sending is {}", lstDtoResponse);
        return new ResponseEntity<ResponseDto<BusinessTypeResponseDto>>(ResponseDto.success(lstDtoResponse), HttpStatus.OK);
    }

    @GetMapping("/all/paging")
    @ApiOperation(value = "دریافت همه انواع کسب و کارها با صفحه بندی", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Page<BusinessTypeResponseDto>>> getBusinessTypesWithPaging(@RequestParam @Valid @ApiParam(value = "شماره صفحه", example = "0", required = true) int pageNo, @RequestParam @Valid @ApiParam(value = "شمارگان اقلام در هر صفحه", example = "10", required = true) int pageSize, @ApiParam(value = "مرتب سازی بر اساس", example = "title") @RequestParam(required = false) String sortName, @ApiParam(value = "جهت مرتب سازی", example = "asc") @RequestParam(required = false) String asc) {
        log.debug("received page number and size for retrieving all businessTypes are {}, {}", pageNo, pageSize);
        String sortColumn = StringUtils.isNoneBlank(sortName) ? sortName : "title";
        String direction = (StringUtils.isNoneBlank(asc) && (asc.equalsIgnoreCase("DESC") || asc.equalsIgnoreCase("asc"))) ? asc : "DESC";
        Sort sort = Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), sortColumn);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<BusinessType> businessTypePage = businessTypeService.getAllByPaging(pageable);
        log.debug("the businessTypePage for sending is {}", businessTypePage);
        return new ResponseEntity<ResponseDto<Page<BusinessTypeResponseDto>>>(ResponseDto.success(businessTypePage), HttpStatus.OK);
    }

    @GetMapping(value = "/{businessTypeId}")
    @ApiOperation(value = "یافتن یک نوع کسب و کار با شناسه", produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<ResponseDto<BusinessTypeResponseDto>> getById(@PathVariable("businessTypeId") @Valid @Min(1) @ApiParam(value = "شناسه استان مورد نظر", example = "1", required = true) long businessTypeId) {
        log.debug("received businessTypeId for retrieving a businessType is {}", businessTypeId);
        BusinessType businessType = businessTypeService.getById(businessTypeId);
        BusinessTypeResponseDto dtoResponse = businessTypeMapper.toDtoResponse(businessType);
        log.debug("the BusinessTypeDto for sending is {}", dtoResponse);
        return new ResponseEntity<ResponseDto<BusinessTypeResponseDto>>(ResponseDto.success(dtoResponse), HttpStatus.OK);
    }

    @GetMapping("/businessType?")
    @ApiOperation(value = "یافتن همه کسب و کارهایی که بخشی از یک واژه را دارند", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<List<BusinessTypeResponseDto>>> search(@RequestParam("name") @Valid @NotNull @ApiParam(value = "بخشی از نام استان مورد نظر", example = "آذر", required = true) String value) {
        log.debug("received value for searching name is {}", value);
        List<BusinessType> businessTypeList = businessTypeService.searchTitle(value);
        List<BusinessTypeResponseDto> businessTypeResponseDtoList = businessTypeMapper.toDtoResponseList(businessTypeList);
        log.debug("the list of businessType for sending is {}", businessTypeResponseDtoList);
        return new ResponseEntity<ResponseDto<List<BusinessTypeResponseDto>>>(ResponseDto.success(businessTypeResponseDtoList), HttpStatus.OK);
    }
}
