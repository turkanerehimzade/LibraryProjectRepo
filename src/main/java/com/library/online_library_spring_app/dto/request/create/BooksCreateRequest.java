package com.library.online_library_spring_app.dto.request.create;

import com.library.online_library_spring_app.dao.entity.Authors;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BooksCreateRequest {
    @NotNull(message = "Book name can not be null")
    private String bookName;
    @NotNull(message="category can not be null")
    private String category;
    @NotNull(message="language can not be null")
    private String language;
    private LocalDate publicationDate;
    private int pageCount;
    private int count;
    private Boolean bookIsActive = true;
    private AuthorsCreateRequest authorsCreateRequest;
}
