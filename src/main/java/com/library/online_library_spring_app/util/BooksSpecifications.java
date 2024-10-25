package com.library.online_library_spring_app.util;

import com.library.online_library_spring_app.dao.entity.Books;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class BooksSpecifications {

    public static Specification<Books> hasBookName(String bookName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("bookName")), "%" + bookName.toLowerCase() + "%");
    }

    public static Specification<Books> hasCategory(String category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category"), category);
    }

    public static Specification<Books> hasLanguage(String language) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("language"), language);
    }

    public static Specification<Books> hasPublishedDateAfter(LocalDate date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("publicationDate"), date);
    }

}
