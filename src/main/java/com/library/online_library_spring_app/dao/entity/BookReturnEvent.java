package com.library.online_library_spring_app.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "returnEvent", schema = "online_library_schema")
public class BookReturnEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="book_id")
    private Long bookId;
    @Column(name="user_id")
    private Long userId;
    private LocalDateTime returnDate;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
