package com.library.online_library_spring_app.dto.request.update;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthorsUpdateRequest {
    private String name;
    private String surname;
    private Timestamp updatedAt;
    private Boolean authorsIsActive;
}
