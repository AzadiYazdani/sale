package com.haraji.app.api;

import com.haraji.app.mapper.UploadedMultipartFileMapper;
import com.haraji.app.model.ThunderUploadedMultipartFile;
import com.haraji.app.model.dto.UploadedMultipartFileDto;
import com.haraji.app.util.FileUploadUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "File Operations", description = "عملیات مربوط به بارگذاری و مدیریت فایل‌ها")
@Slf4j
public class UploadController {

    private final UploadedMultipartFileMapper uploadedMultipartFileMapper;

    public UploadController(UploadedMultipartFileMapper uploadedMultipartFileMapper) {
        this.uploadedMultipartFileMapper = uploadedMultipartFileMapper;
    }

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "بارگذاری فایل", description = "دریافت اطلاعات فایل و ذخیره‌سازی آن در سیستم")
    public ResponseEntity<String> handleFileUpload(
            @RequestBody @Parameter(description = "اطلاعات فایل ارسالی", required = true) UploadedMultipartFileDto fileDto) {

        log.info("Received request to upload file");

        try {
            ThunderUploadedMultipartFile file = uploadedMultipartFileMapper.toModel(fileDto);
            FileUploadUtil.saveFile(file, fileDto.getAttributes());
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            log.error("Error occurred during file upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }
}
