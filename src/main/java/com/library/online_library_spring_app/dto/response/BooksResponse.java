package com.library.online_library_spring_app.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.library.online_library_spring_app.dao.entity.Authors;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BooksResponse {
    private Long id;
    private String bookName;
    private String category;
    private String language;
    private LocalDate publicationDate;
    private int pageCount;
    private int count;
    private boolean bookIsActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private AuthorsResponse authors;



//    @Override
//    public String toString() {
//        return "BooksResponse{" +
//                "bookName='" + bookName + '\'' +
//                ", category='" + category + '\'' +
//                ", language='" + language + '\'' +
//                ", publicationDate=" + publicationDate +
//                ", pageCount=" + pageCount +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
//                '}';
//    }
}
