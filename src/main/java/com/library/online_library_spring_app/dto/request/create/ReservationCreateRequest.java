package com.library.online_library_spring_app.dto.request.create;

import com.library.online_library_spring_app.enums.ReservationStatus;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationCreateRequest {

    private String email;
    private Long bookId;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private ReservationStatus status=ReservationStatus.RESERVED;
    private Timestamp createdAt=new Timestamp(System.currentTimeMillis());
}
