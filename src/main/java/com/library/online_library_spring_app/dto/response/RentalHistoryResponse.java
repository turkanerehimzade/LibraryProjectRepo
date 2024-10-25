package com.library.online_library_spring_app.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RentalHistoryResponse {
    private Long userId;
    private LocalDateTime rentalDate;
}
