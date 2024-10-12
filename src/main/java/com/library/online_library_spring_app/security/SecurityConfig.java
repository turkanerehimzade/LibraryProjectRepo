package com.library.online_library_spring_app.security;

import com.library.online_library_spring_app.exception.JWTAccessDeniedHandler;
import com.library.online_library_spring_app.exception.JWTAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JWTAuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAccessDeniedHandler accessDeniedHandler;


    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    private static final String[] AUTH_WHITELIST = {
            "/swagger-ui.html",
//            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/auth/**",
            "/users/add-user"
    };
    private static final String[] USER_WHITELIST = {
            "/books/by-authors/**",
            "/books/book-id/**",
            "/books/book-name/**",
            "/books/all-book",
            "/add-reservation",
            "/ratings/**",
            "/users/deactivate/**",
            "/users/update/**",
            "/users/add-user"
    };
    private static final String[] ADMIN_WHITELIST = {
            "/books/all-book",
            "/books/add-book",
            "/books/delete-book/**",
            "/books/update-book/**",
            "/books/remove-book/**",
            "/books/inventory",
            "/users/**",
            "/statistics/**",
            "/reservations/**",
            "/ratings/**"
    };
    private static final String[] SUPER_ADMIN_WHITELIST = {
            "/users/**",
            "/books/**",
            "/authors/**",
            "/statistics/**",
            "/reservations/**",
            "/ratings/**",
            "/notification/**"

    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
//                        .anyRequest().authenticated())
                        .requestMatchers(USER_WHITELIST).hasAnyRole("USER")
                        .requestMatchers(ADMIN_WHITELIST).hasAnyRole("ADMIN")
                        .requestMatchers(SUPER_ADMIN_WHITELIST).hasAnyRole("SUPER_ADMIN")
                        .anyRequest().authenticated())


                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint))
                .build();
    }

    @Bean
    public static PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
