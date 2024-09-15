package com.library.online_library_spring_app.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefreshTokenRequest {
    private String refreshToken;
    private String accessToken;
}
