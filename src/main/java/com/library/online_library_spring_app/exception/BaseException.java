package com.library.online_library_spring_app.exception;

import com.library.online_library_spring_app.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private String message;
    private Integer statusCode;
    private Integer httpStatusCode;
    public static BaseException of(ResponseCode responseCode) {
        return  new BaseException(responseCode.getMessage(), responseCode.getStatusCode(), responseCode.getHttpStatusCode());
    }
}
