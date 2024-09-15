package com.library.online_library_spring_app.dao.repository;

import com.library.online_library_spring_app.dao.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findAuthTokenByAccesTokenAndIsActive(String accesToken, Boolean isActive);
}
