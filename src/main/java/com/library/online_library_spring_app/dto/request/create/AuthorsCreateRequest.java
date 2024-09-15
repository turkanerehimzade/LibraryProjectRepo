package com.library.online_library_spring_app.dto.request.create;

import com.library.online_library_spring_app.dao.entity.Books;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthorsCreateRequest {
    private String name;
    private String surname;
    private Boolean authorsIsActive=true;
    private Timestamp createdAt= Timestamp.valueOf(LocalDateTime.now());
    private Set<BooksCreateRequest> booksCreateRequests=new HashSet<>();
}
