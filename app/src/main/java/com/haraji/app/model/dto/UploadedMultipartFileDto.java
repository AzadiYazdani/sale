package com.haraji.app.model.dto;

import io.swagger.annotations.ApiModelProperty;
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
public class UploadedMultipartFileDto implements Serializable {

    @ApiModelProperty(value = "محتوای باینری", dataType = "bytes", required = true, example = "Hello, World!")
    private byte[] bytes;

    @ApiModelProperty(value = "جنس محتوا", dataType = "String", required = true, example = "MediaType.TEXT_PLAIN_VALUE")
    private String contentType;

    @ApiModelProperty(value = "شناسه شهر", dataType = "String", required = true, example = "file")
    private String formParameterName;

    @ApiModelProperty(value = "نام فایل", dataType = "String", required = true, example = "hello.txt")
    private String originalFilename;

    @ApiModelProperty(value = "مشخصات فایل")
    private Map<String, String> attributes;

    private Boolean isEdit;
}
