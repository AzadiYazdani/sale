package com.haraji.common.dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private int status;
    private String errorCode;
    private String message;
    private String localizedMessage;
    private String path;
    private LocalDateTime timestamp;

}
