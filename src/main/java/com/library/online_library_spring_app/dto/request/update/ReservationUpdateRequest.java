package com.library.online_library_spring_app.dto.request.update;

import com.library.online_library_spring_app.dao.entity.Books;
import com.library.online_library_spring_app.dao.entity.Users;
import com.library.online_library_spring_app.enums.ReservationStatus;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationUpdateRequest {


//    private Users user;
//    private Books book;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private ReservationStatus status;
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
}
