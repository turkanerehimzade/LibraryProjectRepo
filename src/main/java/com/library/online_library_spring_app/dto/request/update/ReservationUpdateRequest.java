package com.library.online_library_spring_app.dto.request.update;

import com.library.online_library_spring_app.enums.ReservationStatus;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationUpdateRequest {


    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private ReservationStatus status;
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
}
