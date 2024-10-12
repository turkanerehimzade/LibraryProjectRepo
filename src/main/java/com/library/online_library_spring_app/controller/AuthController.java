package com.library.online_library_spring_app.controller;

import com.library.online_library_spring_app.dao.entity.Users;
import com.library.online_library_spring_app.dto.request.RefreshTokenRequest;
import com.library.online_library_spring_app.dto.request.SignInRequest;
import com.library.online_library_spring_app.dto.response.UserLoginResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.enums.ResponseCode;
import com.library.online_library_spring_app.security.UserPrincipal;
import com.library.online_library_spring_app.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
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
//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity.badRequest().body("Error: Username is already taken!");
//        }
//
//        Users user = new Users();
//        user.setUsername(signUpRequest.getUsername());
//        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword())); // BCrypt ilə şifrələmə
//        userRepository.save(user);
//
//        return ResponseEntity.ok("User registered successfully!");
//    }

}
