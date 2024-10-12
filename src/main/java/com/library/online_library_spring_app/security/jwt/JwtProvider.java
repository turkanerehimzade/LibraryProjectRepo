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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;

@Component
public class JwtProvider {
    @Value("${authentication.jwt.private-key}")
    private String privateKey;

    @Value("${authentication.jwt.public-key}")
    private String publicKey;

    private static final String JWT_HEADER_STRING = "Authorization";
    private static final String JWT_TOKEN_PREFIX = "Bearer";
    //    private PrivateKey jwtPrivateKey;
//    private PublicKey jwtPublicKey;
    private final CustomUserDetailService userDetailsService;
//    private final AuthTokenService authTokenService;

    public JwtProvider(CustomUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
        KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS512);
        this.privateKey = String.valueOf(keyPair.getPrivate());
        this.publicKey = String.valueOf(keyPair.getPublic());
        // İstəyə bağlı olaraq public açarı da əldə edə bilərsiniz
    }


//    private void initializeKeys() {
//        KeyFactory keyFactory = getKeyFactory();
//        try {
//            Base64.Decoder decoder = Base64.getDecoder();
//            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decoder.decode(privateKey));
//            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decoder.decode(publicKey));
//
//            jwtPrivateKey = keyFactory.generatePrivate(privateKeySpec);
//            jwtPublicKey = keyFactory.generatePublic(publicKeySpec);
//        } catch (Exception e) {
//            throw new RuntimeException("Invalid key specification", e);
//        }
//    }

    //
    private PrivateKey preparePrivateKey() {
        try {
//            String privateKeyPEM = publicKey.replace("-----BEGIN PRIVATE KEY-----", "")
//                    .replace("-----END PRIVATE KEY-----", "")
//                    .replaceAll("\\s", "");
            KeyFactory kf = getKeyFactory();

            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            return kf.generatePrivate(keySpecPKCS8);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    private PublicKey preparePublicKey() {
        try {
//            String publicKeyPEM = publicKey.replace("-----BEGIN PUBLIC KEY-----", "")
//                    .replace("-----END PUBLIC KEY-----", "")
//                    .replaceAll("\\s", "");

            KeyFactory kf = getKeyFactory();
            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            return kf.generatePublic(keySpecX509);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateToken(UserPrincipal userPrincipal, Long expireDate) {
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireDate))
                .signWith(preparePrivateKey(), SignatureAlgorithm.RS512)
                .compact();
    }

    public Claims read(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(preparePublicKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSubject(String token) {
        return read(token).getSubject();
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWT_HEADER_STRING);
        if (bearerToken != null && bearerToken.startsWith(JWT_TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String bearerToken = resolveToken( request);
        if (bearerToken == null) {
            return null;
        }
        Claims claims = read(bearerToken);
        String username = claims.getSubject();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return username != null ?
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) : null;
    }

    public boolean isTokenValid(HttpServletRequest request) {
        String bearerToken = resolveToken(request);
        if (bearerToken == null) {
            return false;
        }
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(bearerToken)
                .getBody();

        return !claims.getExpiration().before(new Date());
    }

    private KeyFactory getKeyFactory() {
        try {
            return KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unknown key generation algorithm", e);
        }
    }

}
