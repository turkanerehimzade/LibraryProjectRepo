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


}
