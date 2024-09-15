package com.library.online_library_spring_app.dao.repository;

import com.library.online_library_spring_app.dao.entity.Books;
import com.library.online_library_spring_app.dto.response.BooksResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {
    @Query("SELECT b FROM Books b WHERE LOWER(b.authors.name) LIKE LOWER(CONCAT('%', :authorName, '%')) AND LOWER(b.authors.surname) LIKE LOWER(CONCAT('%', :authorSurname, '%'))")
    List<Books> findBooksByAuthors_NameAndAuthors_Surname(String authorName, String authorSurname);

    //    @Query("SELECT b FROM Books b WHERE LOWER(b.bookName) = LOWER(:bookName)")
    List<Books> findBooksByBookName(String booksName);


//    List<Books> findBooksWithCount();
}
