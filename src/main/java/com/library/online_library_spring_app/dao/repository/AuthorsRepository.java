package com.library.online_library_spring_app.dao.repository;

import com.library.online_library_spring_app.dao.entity.Authors;
import com.library.online_library_spring_app.dao.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorsRepository extends JpaRepository<Authors, Long> {
    List<Authors> findAuthorsByBooks_id(Long book_id);

    @Query("SELECT a FROM Authors a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))") //boyuk kicik ferqi yoxdur
    List<Authors> findAuthorsByName(String name);

}
