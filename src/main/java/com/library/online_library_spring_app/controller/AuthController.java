package com.library.online_library_spring_app.controller;

import com.library.online_library_spring_app.dto.request.SignInRequest;
import com.library.online_library_spring_app.dto.response.UserLoginResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@SecurityRequirement(name = "Authorization")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public SuccessResponse<UserLoginResponse> signIn(@RequestBody SignInRequest request){
        return authenticationService.signInAndReturnJWT(request);
    }
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse<Object> logout(@RequestHeader("Authorization")String accessToken){
        return authenticationService.logout(accessToken.substring(7));

    }
    @PostMapping("/refresh-token")
    public SuccessResponse<UserLoginResponse> refreshToken( HttpServletRequest authorizationHeader){
        return authenticationService.refreshToken(authorizationHeader);
    }

}
