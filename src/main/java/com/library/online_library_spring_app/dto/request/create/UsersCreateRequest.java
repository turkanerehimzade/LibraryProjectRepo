package com.library.online_library_spring_app.dto.request.create;

import jakarta.persistence.Column;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsersCreateRequest {
    private String username;
    private String password;
    private Boolean userIsActive=true;
    private Timestamp createdAt=new Timestamp(System.currentTimeMillis());
}
