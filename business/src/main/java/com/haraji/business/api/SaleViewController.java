package com.haraji.business.api;

import com.haraji.business.mapper.SaleMapper;
import com.haraji.business.model.SaleView;
import com.haraji.business.service.SaleViewService;
import com.haraji.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
    @Operation(summary = "یافتن همه حراجی ها بر اساس شناسه شهر و شناسه نوع کسب و کار")
    public ResponseEntity<ResponseDto<List<SaleResponseDto>>> getAllSales(
            @RequestParam("city") @Valid @NotNull @Parameter(description = "شناسه شهر مورد نظر", example = "2", required = true) String cityId,
            @RequestParam("businessType") @Valid @NotNull @Parameter(description = "شناسه کسب و کار مورد نظر", example = "2", required = true) String businessTypeId
    ) {
        log.debug("received value for getAllSales is city {} and businessType {}", cityId, businessTypeId);
        List<SaleView> saleList = saleViewService.getAllSalesByCityAndBusinessType(Integer.parseInt(cityId), Integer.parseInt(businessTypeId));
        List<SaleResponseDto> saleResponseDtoList = saleMapper.toDtoResponseList(saleList);
        log.debug("the list of sales for sending is {}", saleResponseDtoList);
        return new ResponseEntity<>(ResponseDto.success(saleResponseDtoList), HttpStatus.OK);
    }

    @GetMapping(value = "/{saleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "یافتن یک حراجی با شناسه")
    public ResponseEntity<ResponseDto<SaleResponseDto>> getById(
            @PathVariable("saleId") @Valid @Min(1) @Parameter(description = "شناسه حراج مورد نظر", example = "1", required = true) int saleId) {

        log.debug("received saleId for retrieving a sale is {}", saleId);
        SaleView sale = saleViewService.getById(saleId);
        SaleResponseDto dtoResponse = saleMapper.toDtoResponse(sale);
        log.debug("the SaleDto for sending is {}", dtoResponse);
        return new ResponseEntity<>(ResponseDto.success(dtoResponse), HttpStatus.OK);
    }
}
