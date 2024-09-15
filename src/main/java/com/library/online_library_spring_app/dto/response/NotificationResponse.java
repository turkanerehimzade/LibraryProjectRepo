package com.library.online_library_spring_app.dto.response;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.online_library_spring_app.enums.NotificationStatus;
import com.library.online_library_spring_app.enums.NotificationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponse {
    private String message;
    private LocalDateTime sentAt;
    private String user;
    private NotificationType type;
}
