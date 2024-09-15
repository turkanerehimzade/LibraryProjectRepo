package com.library.online_library_spring_app.dto.response.reservationResponse;

import com.library.online_library_spring_app.dao.entity.Books;
import com.library.online_library_spring_app.dao.entity.Users;
import com.library.online_library_spring_app.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReservationResponse {
    private Users user;
    private Books book;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private ReservationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
