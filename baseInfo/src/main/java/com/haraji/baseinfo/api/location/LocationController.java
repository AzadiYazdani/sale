package com.haraji.baseinfo.api.location;

import com.haraji.baseinfo.api.location.city.CityResponseDto;
import com.haraji.baseinfo.api.location.state.StateResponseDto;
import com.haraji.baseinfo.mapper.location.CityMapper;
import com.haraji.baseinfo.mapper.location.StateMapper;
import com.haraji.baseinfo.model.location.City;
import com.haraji.baseinfo.model.location.State;
import com.haraji.baseinfo.service.location.state.StateService;
import com.haraji.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
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

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@RestController
@Tag(name = "Location operations")
@RequestMapping("/location/states")
@Slf4j
@Validated
public class LocationController {

    private final StateService stateService;
    private final StateMapper stateMapper;
    private final CityMapper cityMapper;

    public LocationController(StateService stateService, StateMapper stateMapper, CityMapper cityMapper) {
        this.stateService = stateService;
        this.stateMapper = stateMapper;
        this.cityMapper = cityMapper;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "یافتن همه استانها")
    public ResponseEntity<ApiResponse<List<StateResponseDto>>> getAllStates() {
        List<State> state = stateService.getAll();
        List<StateResponseDto> lstDtoResponse = stateMapper.toDtoResponseList(state);
        log.debug("the StateDto for sending is {}", lstDtoResponse);
        return ResponseEntity.ok(ApiResponse.success(lstDtoResponse));
    }

    @GetMapping(value = "/paging", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "دریافت همه استان‌ها با صفحه‌بندی")
    public ResponseEntity<ApiResponse<Page<StateResponseDto>>> getStatesWithPaging(
            @RequestParam @Min(0) @Parameter(description = "شماره صفحه", example = "0", required = true)
            int pageNo,
            @RequestParam @Min(1) @Parameter(description = "تعداد اقلام در هر صفحه", example = "10", required = true)
            int pageSize,
            @RequestParam(required = false) @Parameter(description = "مرتب سازی بر اساس", example = "title")
            String sortName,
            @RequestParam(required = false) @Parameter(description = "جهت مرتب سازی", example = "DESC") String asc) {

        log.debug("Received pageNo={} pageSize={}", pageNo, pageSize);
        String sortColumn = StringUtils.isNotBlank(sortName) ? sortName : "title";
        String direction = (StringUtils.isNotBlank(asc)
                && ("ASC".equalsIgnoreCase(asc) || "DESC".equalsIgnoreCase(asc)))
                ? asc : "DESC";

        Pageable pageable = PageRequest.of(
                pageNo,
                pageSize,
                Sort.by(Sort.Direction.fromString(direction), sortColumn));

        Page<StateResponseDto> response = stateService.getAllByPaging(pageable).map(stateMapper::toDtoResponse);
        log.debug("State page: {}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping(value = "/{stateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "یافتن یک استان با شناسه")
    public ResponseEntity<ApiResponse<StateResponseDto>> getById(
            @PathVariable @Min(1) @Parameter(description = "شناسه استان مورد نظر", example = "1", required = true) int stateId) {

        log.debug("Received stateId={}", stateId);
        State state = stateService.getById(stateId);
        StateResponseDto response = stateMapper.toDtoResponse(state);
        log.debug("State response={}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping(value = "/{stateId}/cities", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "یافتن همه شهرهای یک استان")
    public ResponseEntity<ApiResponse<List<CityResponseDto>>> getAllCities(
            @PathVariable @Min(1) @Parameter(description = "شناسه استان مورد نظر", example = "1", required = true) int stateId) {

        log.debug("Received stateId={}", stateId);
        List<City> cities = stateService.getAllCitiesById(stateId);
        List<CityResponseDto> response = cityMapper.toDtoResponseList(cities);
        log.debug("Cities response={}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "یافتن همه استان‌هایی که بخشی از یک واژه را دارند")
    public ResponseEntity<ApiResponse<List<StateResponseDto>>> search(
            @RequestParam("name") @NotBlank @Parameter(description = "بخشی از نام استان مورد نظر", example = "آذر", required = true)
            String value) {

        log.debug("Received search value={}", value);
        List<State> states = stateService.searchTitle(value);
        List<StateResponseDto> response = stateMapper.toDtoResponseList(states);
        log.debug("Search result={}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
