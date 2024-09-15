package com.library.online_library_spring_app.dto.response.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
//@NoArgsConstructor
//@AllArgsConstructor
public class ErrorResponse<T> extends BaseResponse<T> {
    public ErrorResponse(String message, Integer statusCode) {
        super(message,statusCode);
    }
}
