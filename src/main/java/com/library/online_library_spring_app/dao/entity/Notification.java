package com.library.online_library_spring_app.dao.entity;

import com.library.online_library_spring_app.enums.NotificationMessage;
import com.library.online_library_spring_app.enums.NotificationStatus;
import com.library.online_library_spring_app.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "notification", schema = "online_library_schema")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NotificationMessage message;
    @Column(name="send_at")
    private LocalDateTime sentAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Users users;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="book_id")
    private Books books;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="event_id")
    private Event event;

}
