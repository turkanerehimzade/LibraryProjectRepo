package com.library.online_library_spring_app.dto.request.create;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookReturnEventCreateRequest {
    private Long bookId;
    private Long userId;
    private LocalDateTime returnDate=LocalDateTime.now();
    private Timestamp createdAt=Timestamp.valueOf(returnDate);
    private Timestamp updatedAt;
}
