package com.library.online_library_spring_app.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FilterBookRequest {
    private String bookName;
    private String category;
    private String language;
    private LocalDate publicationDate;

}
