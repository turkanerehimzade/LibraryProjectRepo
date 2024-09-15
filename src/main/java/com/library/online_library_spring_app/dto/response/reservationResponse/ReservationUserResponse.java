package com.library.online_library_spring_app.dto.response.reservationResponse;

import com.library.online_library_spring_app.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReservationUserResponse {
//    private Users user;
//    private Books book;
    private String bookName;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private ReservationStatus status;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
}
