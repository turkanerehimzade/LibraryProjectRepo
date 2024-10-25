package com.library.online_library_spring_app.dto.request.create;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthorsCreateRequest {
    private String name;
    private String surname;
    private Boolean authorsIsActive=true;
    private Timestamp createdAt= Timestamp.valueOf(LocalDateTime.now());
}
