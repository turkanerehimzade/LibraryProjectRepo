package com.library.online_library_spring_app.dao.repository;

import com.library.online_library_spring_app.dao.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("select r from UserRole r where r.user.id = :userId")
    List<UserRole> findRolesByUserId(@Param("userId") Long userId);
}
