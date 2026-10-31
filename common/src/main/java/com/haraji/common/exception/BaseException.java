
package com.haraji.common.exception;

import com.haraji.common.constant.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

public class BaseException extends RuntimeException {

    @Getter
    private final ErrorCode errorCode;
    @Getter
    private final HttpStatus status;
    @Getter
    private final String messageKey;
    private final Object[] args;

    public BaseException(ErrorCode errorCode, HttpStatus status, String messageKey, Object... args) {
        super(messageKey);
        this.errorCode = errorCode;
        this.status = status;
        this.messageKey = messageKey;
        this.args = args == null ? new Object[0] : Arrays.copyOf(args, args.length);
    }

    public Object[] getArgs() {
        return Arrays.copyOf(args, args.length);
    }
}
