package com.library.online_library_spring_app.dao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "rating_and_review", schema = "online_library_schema")
public class RatingAndReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Books book;
    @Column(name="user_id")
    private Long userId;
    private Integer rating;
    private String review;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @JsonProperty("bookName")
    public String getBookName() {
        return book.getBookName();
    }
}
