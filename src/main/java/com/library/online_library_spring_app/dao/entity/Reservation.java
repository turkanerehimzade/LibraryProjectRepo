package com.library.online_library_spring_app.dao.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.library.online_library_spring_app.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "reservations", schema = "online_library_schema")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Books book;

    @Column(name = "reservation_start")
    private LocalDateTime reservationStart;

    @Column(name = "reservation_end")
    private LocalDateTime reservationEnd;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReservationStatus status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @JsonProperty("bookName")
    public String getBookName() {
        return book.getBookName();
    }
    @JsonProperty("username")
    public String getUsername() {
        return user.getUsername();
    }


}
