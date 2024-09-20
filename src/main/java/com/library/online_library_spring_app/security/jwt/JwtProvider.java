package com.library.online_library_spring_app.security.jwt;

import com.library.online_library_spring_app.dto.request.RefreshTokenRequest;
import com.library.online_library_spring_app.security.CustomUserDetailService;
import com.library.online_library_spring_app.security.UserPrincipal;
import com.library.online_library_spring_app.service.AuthTokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${authentication.jwt.private-key}")
    private String privateKey;

    @Value("${authentication.jwt.public-key}")
    private String publicKey;

    private static final String JWT_HEADER_STRING = "Authorization";
    private static final String JWT_TOKEN_PREFIX = "Bearer";
    private PrivateKey jwtPrivateKey;
    private PublicKey jwtPublicKey;
    private final CustomUserDetailService userDetailsService;
    private final AuthTokenService authTokenService;

    public JwtProvider(CustomUserDetailService userDetailsService, AuthTokenService authTokenService) {
        this.userDetailsService = userDetailsService;
        this.authTokenService = authTokenService;
        initializeKeys();
    }

    private void initializeKeys() {
        KeyFactory keyFactory = getKeyFactory();
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decoder.decode(privateKey));
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decoder.decode(publicKey));

            jwtPrivateKey = keyFactory.generatePrivate(privateKeySpec);
            jwtPublicKey = keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            throw new RuntimeException("Invalid key specification", e);
        }
    }



    public String generateToken(UserPrincipal userPrincipal, Long expireDate) {
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + expireDate))
                .signWith(jwtPrivateKey, SignatureAlgorithm.RS512)
                .compact();
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWT_HEADER_STRING);
        if (bearerToken != null && bearerToken.startsWith(JWT_TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String bearerToken = resolveToken(request);
        if (bearerToken == null) {
            return null;
        }
        authTokenService.checkAccessToken(bearerToken);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtPublicKey)
                .build()
                .parseClaimsJws(bearerToken).getBody();

        String username = claims.getSubject();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return username != null ?
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) : null;
    }

//    public boolean isTokenValid(HttpServletRequest request) {
//        String bearerToken = resolveToken(request);
//        if (bearerToken == null) {
//            return false;
//        }
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(jwtPublicKey)
//                .build()
//                .parseClaimsJws(bearerToken)
//                .getBody();
//
//        return !claims.getExpiration().before(new Date());
//    }

    private KeyFactory getKeyFactory() {
        try {
            return KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unknown key generation algorithm", e);
        }
    }
}
