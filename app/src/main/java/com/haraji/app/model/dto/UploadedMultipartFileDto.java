package com.haraji.app.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "مدل انتقال اطلاعات فایل بارگذاری شده")
public class UploadedMultipartFileDto implements Serializable {

    @Schema(description = "محتوای باینری فایل", requiredMode = Schema.RequiredMode.REQUIRED, example = "SGVsbG8sIFdvcmxkIQ==")
    private byte[] bytes;

    @Schema(description = "نوع محتوا (Content-Type)", requiredMode = Schema.RequiredMode.REQUIRED, example = "text/plain")
    private String contentType;

    @Schema(description = "نام پارامتر فرم", requiredMode = Schema.RequiredMode.REQUIRED, example = "file")
    private String formParameterName;

    @Schema(description = "نام اصلی فایل", requiredMode = Schema.RequiredMode.REQUIRED, example = "hello.txt")
    private String originalFilename;

    @Schema(description = "مشخصات و ویژگی‌های اضافی فایل")
    private Map<String, String> attributes;

    @Schema(description = "وضعیت ویرایش فایل")
    private Boolean isEdit;
}
