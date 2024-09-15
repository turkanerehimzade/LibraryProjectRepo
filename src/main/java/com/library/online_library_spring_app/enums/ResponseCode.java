package com.library.online_library_spring_app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS("success",200,200),
    ERROR("Create request failed",400,400);
    private final String message;
    private final int statusCode;
    private final int httpStatusCode;



}
