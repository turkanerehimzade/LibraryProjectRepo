package com.library.online_library_spring_app.dao.repository;

import com.library.online_library_spring_app.dao.entity.Books;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
    @Query("SELECT b FROM Books b WHERE LOWER(b.authors.name) LIKE LOWER(CONCAT('%', :authorName, '%')) " +
            "AND LOWER(b.authors.surname) LIKE LOWER(CONCAT('%', :authorSurname, '%')) " +
            "AND b.bookIsActive = true")
    List<Books> findBooksByAuthors_NameAndAuthors_Surname(String authorName, String authorSurname);
    @Query("SELECT b FROM Books b WHERE b.id = :id AND b.bookIsActive=true")
    Optional<Books> findById(@Param("id") Long id);

    @Query("SELECT b FROM Books b WHERE b.id = :id AND b.bookIsActive=true")
    Optional<List<Books>> findAllById(@Param("id") Long id);

    @Query("SELECT b FROM Books b WHERE LOWER(b.bookName) = LOWER(:bookName) AND b.bookIsActive=true")
    List<Books> findBooksByBookName(String bookName);
    List<Books> findAll(Specification<Books> spec);
}
