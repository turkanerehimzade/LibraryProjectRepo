package com.library.online_library_spring_app.controller;

import com.library.online_library_spring_app.dto.request.RefreshTokenRequest;
import com.library.online_library_spring_app.dto.request.SignInRequest;
import com.library.online_library_spring_app.dto.response.UserLoginResponse;
import com.library.online_library_spring_app.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> signIn(@RequestBody SignInRequest request){
        return ResponseEntity.ok(authenticationService.signInAndReturnJWT(request));
    }
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestHeader("Authorization")String accessToken){
        authenticationService.logout(accessToken.substring(7));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<UserLoginResponse> refreshToken(@RequestBody HttpServletRequest request){
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
