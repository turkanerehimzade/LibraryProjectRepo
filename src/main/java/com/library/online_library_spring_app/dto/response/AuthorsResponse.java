package com.library.online_library_spring_app.dto.response;

import com.library.online_library_spring_app.dao.entity.Books;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AuthorsResponse {
//    private Long id;
    private String name;
    private String surname;
    private Boolean authorsIsActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
//    private Set<BooksResponse> books;


}
