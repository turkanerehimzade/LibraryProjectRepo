package com.library.online_library_spring_app.dto.response.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private String message;
    private Integer statusCode;


//    private T data;
//
//    public static <R> BaseResponse<R> buildBaseResponse(R data, ResponseCode responseCode) {
//        return BaseResponse.<R>builder()
//                .statusCode(responseCode.getStatusCode())
////                .httpStatusCode(responseCode.getHttpStatusCode())
//                .message(responseCode.getMessage())
//                .data(data)
//                .build();
//    }
}
