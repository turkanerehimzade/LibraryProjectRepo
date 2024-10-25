package com.library.online_library_spring_app.dto.request.update;

import com.library.online_library_spring_app.dto.request.create.AuthorsCreateRequest;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BooksUpdateRequest {
    private String bookName;
    private String category;
    private String language;
    private LocalDate publicationDate;
    private Integer pageCount;
    private Integer count;
    private Boolean bookIsActive;
    private Timestamp updatedAt;
}
