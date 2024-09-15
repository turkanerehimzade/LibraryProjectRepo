package com.library.online_library_spring_app.service;

import com.library.online_library_spring_app.dao.entity.AuthToken;
import com.library.online_library_spring_app.dao.repository.AuthTokenRepository;
import com.library.online_library_spring_app.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class AuthTokenService {
    private final AuthTokenRepository authTokenRepository;

    public void deactiveAccessToken(String accessToken) {
        String token = encryptAccessToken(accessToken);
        AuthToken authToken = authTokenRepository.findAuthTokenByAccesTokenAndIsActive(token, true)
                .orElseThrow(() -> new NotFoundException("Auth token not found"));
        authToken.setIsActive(false);
        authTokenRepository.save(authToken);
    }

    private String encryptAccessToken(String accessToken) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(accessToken.getBytes());
            byte[] digest = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void checkAccessToken(String accessToken) {
        String token = encryptAccessToken(accessToken);
        System.err.println(token);
        authTokenRepository.findAuthTokenByAccesTokenAndIsActive(token, true)
                .orElseThrow(() -> new NotFoundException("Auth token not found"));
    }

    public void saveTokenInfo(UserPrincipal userPrincipal, String accesToken, String refreshToken) {
        AuthToken authToken = new AuthToken();
        authToken.setAccesToken(accesToken);
        authToken.setRefreshToken(refreshToken);
        authToken.setUserPrincipal(userPrincipal);
        authTokenRepository.save(authToken);
    }

}
