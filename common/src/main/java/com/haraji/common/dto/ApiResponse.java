package com.haraji.common.dto;

import lombok.*;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    /**
     * HTTP Status Code
     */
    private int status;

    /**
     * پیام پاسخ
     */
    private String message;

    /**
     * داده اصلی
     */
    private T data;

    /**
     * زمان پاسخ
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> ApiResponse<T> success(T data) {

        return ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {

        return ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

}
