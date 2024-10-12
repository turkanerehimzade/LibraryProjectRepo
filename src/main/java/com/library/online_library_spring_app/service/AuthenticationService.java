package com.library.online_library_spring_app.service;

import com.library.online_library_spring_app.dto.request.RefreshTokenRequest;
import com.library.online_library_spring_app.dto.request.SignInRequest;
import com.library.online_library_spring_app.dto.response.UserLoginResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.enums.ResponseCode;
import com.library.online_library_spring_app.security.UserPrincipal;
import com.library.online_library_spring_app.security.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service

public class AuthenticationService {
    @Value("${authentication.jwt.access.expiration-in-ms}")
    private  Long JWT_ACCESS_EXPIRATION_IN_MS;
    @Value("${authentication.jwt.refresh.expiration-in-ms}")
    private Long JWT_REFRESH_EXPIRATION_IN_MS;

    private final AuthenticationManager authenticationManager;
    private  final JwtProvider jwtProvider;
    private final AuthTokenService authTokenService;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtProvider jwtProvider, AuthTokenService authTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.authTokenService = authTokenService;
    }

    private UserLoginResponse getLoginResponse(UserPrincipal userPrincipal) {
        String accessToken = jwtProvider.generateToken(userPrincipal, JWT_ACCESS_EXPIRATION_IN_MS);
        String refreshToken=jwtProvider.generateToken(userPrincipal, JWT_REFRESH_EXPIRATION_IN_MS);
        System.out.println(accessToken);
        System.out.println(refreshToken);
        authTokenService.saveTokenInfo(userPrincipal,accessToken,refreshToken);
        return new UserLoginResponse().setAccessToken(accessToken).setRefreshToken(refreshToken);
    }
    public SuccessResponse<UserLoginResponse> signInAndReturnJWT(SignInRequest signInRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),signInRequest.getPassword())
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return SuccessResponse.createSuccessResponse(getLoginResponse(userPrincipal), ResponseCode.SUCCESS);
    }
    public SuccessResponse<Object> logout(String accessToken) {
        authTokenService.deactiveAccessToken(accessToken);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);

    }
    public SuccessResponse<UserLoginResponse> refreshToken(HttpServletRequest authorizationHeader){

        Authentication authentication= jwtProvider.getAuthentication(authorizationHeader);
        return SuccessResponse.createSuccessResponse(getLoginResponse((UserPrincipal) authentication.getPrincipal()), ResponseCode.SUCCESS);
    }
}
