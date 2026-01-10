package com.haraji.baseinfo.api.util;


import com.haraji.common.dto.ResponseDto;
import com.haraji.common.util.DateUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@Api(value = "Util operations")
@RequestMapping("/util")
@Slf4j
@Validated
public class UtilController {


    @GetMapping(value = "/date")
    @ApiOperation(value = "تبدیل زمان", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<LocalDate>> getAllStates(@RequestParam @Valid @ApiParam(value = "تاریخ", example = "1/1/1358", required = true) String date ) {
        try {
            LocalDate localdate = DateUtility.getLocalDateFromString(date);
            return new ResponseEntity<ResponseDto<LocalDate>>(ResponseDto.success(localdate), HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<ResponseDto<LocalDate>>(ResponseDto.success(null), HttpStatus.BAD_REQUEST);
        }
    }

}
