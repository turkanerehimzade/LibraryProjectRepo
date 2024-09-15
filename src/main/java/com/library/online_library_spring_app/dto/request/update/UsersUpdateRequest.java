package com.library.online_library_spring_app.dto.request.update;

import jakarta.persistence.Column;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsersUpdateRequest {
    private String username;
    private String password;
    private Boolean userIsActive;
    private Timestamp updatedAt;
}
