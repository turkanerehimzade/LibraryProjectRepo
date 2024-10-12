package com.library.online_library_spring_app.dao.repository;

import com.library.online_library_spring_app.dao.entity.Users;
import com.library.online_library_spring_app.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);

    @Query("SELECT u FROM Users u JOIN u.roles r WHERE r.roleName = :roleName")
    List<Users> findUsersByRoleName(@Param("roleName") RoleName roleName);
}
