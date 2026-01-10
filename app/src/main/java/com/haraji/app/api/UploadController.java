package com.haraji.app.api;

import com.haraji.app.mapper.UploadedMultipartFileMapper;
import com.haraji.app.model.ThunderUploadedMultipartFile;
import com.haraji.app.model.dto.UploadedMultipartFileDto;
import com.haraji.app.util.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/file")
@Api(value = "عملیات فایل")
@Slf4j
public class UploadController {

    private final UploadedMultipartFileMapper uploadedMultipartFileMapper;

    public UploadController(UploadedMultipartFileMapper uploadedMultipartFileMapper) {
        this.uploadedMultipartFileMapper = uploadedMultipartFileMapper;
    }

    @PostMapping(value = "/upload")
    @ApiOperation(value = "بارگزاری فایل", produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<?> handleFileUpload( @RequestBody UploadedMultipartFileDto fileDto ) {
        log.info("upload file");

        try {
            ThunderUploadedMultipartFile file = uploadedMultipartFileMapper.toModel(fileDto);
            FileUploadUtil.saveFile(file, fileDto.getAttributes());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok("File uploaded successfully.");
    }
}
