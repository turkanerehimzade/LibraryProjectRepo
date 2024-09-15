package com.library.online_library_spring_app.dto.request.create;

import com.library.online_library_spring_app.dao.entity.Books;
import com.library.online_library_spring_app.dao.entity.Users;
import com.library.online_library_spring_app.enums.NotificationMessage;
import com.library.online_library_spring_app.enums.NotificationStatus;
import com.library.online_library_spring_app.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
