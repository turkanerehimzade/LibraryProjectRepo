package com.library.online_library_spring_app.dao.repository;

import com.library.online_library_spring_app.dao.entity.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Authors, Long> {

    @Query("SELECT a FROM Authors a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%')) And a.authorsIsActive = true")
    List<Authors> findAuthorsByName(String name);

    @Query("SELECT a FROM Authors a WHERE a.authorsIsActive = true")
    List<Authors> findAll();
    @Query("SELECT a FROM Authors a WHERE a.id = :id AND a.authorsIsActive = true")
    Optional<Authors> findById(Long id);
}
