package com.haraji.business.api;

import com.haraji.business.mapper.SaleMapper;
import com.haraji.business.model.SaleView;
import com.haraji.business.service.SaleViewService;
import com.haraji.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@RestController
@Tag(name = "Sale operations", description = "عملیات مربوط به حراجی‌ها")
@RequestMapping("/sale")
@Slf4j
@Validated
public class SaleViewController {

    private final SaleViewService saleViewService;
    private final SaleMapper saleMapper;

    public SaleViewController(SaleViewService saleViewService, SaleMapper saleMapper) {
        this.saleViewService = saleViewService;
        this.saleMapper = saleMapper;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "یافتن همه حراجی‌ها بر اساس شناسه شهر و شناسه نوع کسب‌وکار")
    public ResponseEntity<ApiResponse<List<SaleResponseDto>>> getAllSales(

            @RequestParam("city")
            @NotNull
            @Min(1)
            @Parameter(description = "شناسه شهر مورد نظر", example = "2", required = true) Integer cityId,

            @RequestParam("businessType")
            @NotNull
            @Min(1)
            @Parameter(description = "شناسه نوع کسب‌وکار", example = "2", required = true) Integer businessTypeId) {

        log.debug("received cityId={} and businessTypeId={}", cityId, businessTypeId);
        List<SaleView> saleList = saleViewService.getAllSalesByCityAndBusinessType(cityId, businessTypeId);
        List<SaleResponseDto> response = saleMapper.toDtoResponseList(saleList);
        log.debug("sending {} sales", response.size());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping(value = "/{saleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "یافتن یک حراجی با شناسه")
    public ResponseEntity<ApiResponse<SaleResponseDto>> getById(
            @PathVariable("saleId") @Min(1) @Parameter(description = "شناسه حراج مورد نظر", example = "1", required = true) Integer saleId) {

        log.debug("received saleId={} for retrieving sale", saleId);
        SaleView sale = saleViewService.getById(saleId);
        SaleResponseDto response = saleMapper.toDtoResponse(sale);
        log.debug("sending sale={}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
