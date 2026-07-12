package com.haraji.common.dto;

import lombok.Builder;
import lombok.Data;

@Data

public class ApiResponse<T> {

        private int code;
        private String message;
        private T data;
        private long timestamp;

        public ApiResponse(int code, String message, T data) {
            this.code = code;
            this.message = message;
            this.data = data;
            this.timestamp = System.currentTimeMillis();
        }

        // متدهای کمکی برای ساخت پاسخ موفق و ناموفق
        public static <T> ApiResponse<T> success(T data) {
            return new ApiResponse<>(200, "success", data);
        }

        public static <T> ApiResponse<T> error(int code, String message) {
            return new ApiResponse<>(code, message, null);
        }

}
