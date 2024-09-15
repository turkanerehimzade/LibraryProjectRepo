package com.library.online_library_spring_app.dto.response.base;

import com.library.online_library_spring_app.enums.ResponseCode;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@Data
public class SuccessResponse<T> extends BaseResponse<T> {
    private T data;

    //    public SuccessResponse(String message, Integer statusCode) {
//        super(message,statusCode);
    public static <R> SuccessResponse<R> createSuccessResponse(R data, ResponseCode responseCode) {
        return SuccessResponse
                .<R>builder()
                .statusCode(responseCode.getStatusCode())
                .message(responseCode.getMessage())
                .data(data)
                .build();

    }
}
