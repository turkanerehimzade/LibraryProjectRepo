package com.library.online_library_spring_app.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BooksInventorResponse {
    private String bookName;
    private String language;
    private int count;
}
