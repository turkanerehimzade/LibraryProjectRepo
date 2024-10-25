package com.library.online_library_spring_app.dto.request.create;

import com.library.online_library_spring_app.enums.NotificationMessage;
import com.library.online_library_spring_app.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotificationCreateRequest {
    private NotificationMessage message;
    private LocalDateTime sentAt;
    private Long userId;
    private Long bookId;
    private NotificationType type;
    private Long eventId;
}
