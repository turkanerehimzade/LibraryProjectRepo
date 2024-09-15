package com.library.online_library_spring_app.mapper;

import com.library.online_library_spring_app.dao.entity.Notification;
import com.library.online_library_spring_app.dto.request.create.NotificationCreateRequest;
import com.library.online_library_spring_app.dto.response.NotificationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toNotification (NotificationCreateRequest notificationCreateRequest);
    NotificationResponse toNotificationResponse (Notification notification);
}
