package com.library.online_library_spring_app.security;

import com.library.online_library_spring_app.exception.JWTAccessDeniedHandler;
import com.library.online_library_spring_app.exception.JWTAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JWTAuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter, JWTAuthenticationEntryPoint authenticationEntryPoint, JWTAccessDeniedHandler accessDeniedHandler) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v3 (OpenAPI)
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api/v1/auth/sign-up",
            "/api/v1/auth/login"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // CSRF qorumasını söndürmək üçün bu yazılış istifadə olunur
                .cors(cors -> cors.and()) // CORS-u aktiv edir
                .formLogin(formLogin -> formLogin.disable()) // Form əsaslı login funksiyasını söndürür
                .httpBasic(httpBasic -> httpBasic.disable()) // HTTP Basic auth funksiyasını söndürür

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS).permitAll() // OPTIONS metoduna icazə verir
                        .requestMatchers(AUTH_WHITELIST).permitAll() // İcazə verilən URL-lərə icazə verir
                        .requestMatchers("/**").authenticated()) // Qalan bütün URL-lərə autentifikasiya tələb edir

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sessiyanı stateless edir
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class) // JWT filtresini əlavə edir
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler) // İcazə rədd olunduqda handler-i işə salır
                        .authenticationEntryPoint(authenticationEntryPoint)) // Autentifikasiyada səhv olduqda handler-i işə salır
                .build(); // Konfiqurasiyanı tamamlayır
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


}
