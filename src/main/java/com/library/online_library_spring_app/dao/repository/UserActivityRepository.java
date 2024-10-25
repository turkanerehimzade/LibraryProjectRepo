package com.library.online_library_spring_app.dao.repository;

import com.library.online_library_spring_app.dao.entity.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
    @Query("SELECT ua FROM UserActivity ua WHERE ua.user.id = :userId ")
    Optional<List<UserActivity>> findByUserId(Long userId);
}
