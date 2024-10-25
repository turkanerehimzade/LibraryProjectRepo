package com.library.online_library_spring_app.dao.repository;

import com.library.online_library_spring_app.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role , Long> {
}
