package com.library.online_library_spring_app.dto.response;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UsersResponse {
    private String username;
    private String password;
    private Boolean userIsActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
