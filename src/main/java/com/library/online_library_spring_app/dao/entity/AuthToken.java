package com.library.online_library_spring_app.dao.entity;

import com.library.online_library_spring_app.security.UserPrincipal;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "authToken", schema = "online_library_schema")
public class AuthToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "access_token")
    private String accesToken;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name="is_active")
    private Boolean isActive;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    private UserPrincipal userPrincipal;

}
