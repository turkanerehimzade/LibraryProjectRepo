package com.library.online_library_spring_app.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SignInRequest {
    private String username;
    private String password;
}
