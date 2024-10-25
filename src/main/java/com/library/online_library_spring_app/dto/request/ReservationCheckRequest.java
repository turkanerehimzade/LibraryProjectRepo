package com.library.online_library_spring_app.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationCheckRequest {
    private Long bookId;
    private LocalDateTime checkStartDate;
    private LocalDateTime checkEndDate;
}
