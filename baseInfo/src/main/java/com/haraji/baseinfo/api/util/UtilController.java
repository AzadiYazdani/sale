package com.haraji.baseinfo.api.util;

import com.haraji.common.dto.ApiResponse;
import com.haraji.common.exception.InvalidFormatException;
import com.haraji.common.util.DateUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Tag(name = "Util operations")
@RequestMapping("/util")
@Slf4j
@Validated
public class UtilController {

    @GetMapping(value = "/date", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "تبدیل زمان")
    public ResponseEntity<ApiResponse<LocalDate>> getDate(
            @RequestParam @NotBlank @Parameter(description = "تاریخ", example = "1358/01/01", required = true)
            String date) {
        log.debug("Received date={}", date);
        try {
            LocalDate response = DateUtility.getLocalDateFromString(date);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception ex) {
            throw new InvalidFormatException();
        }
    }
}
