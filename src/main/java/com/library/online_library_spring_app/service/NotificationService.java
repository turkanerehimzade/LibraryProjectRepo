package com.library.online_library_spring_app.service;

import com.library.online_library_spring_app.dao.entity.Books;
import com.library.online_library_spring_app.dao.entity.Event;
import com.library.online_library_spring_app.dao.entity.Notification;
import com.library.online_library_spring_app.dao.entity.Users;
import com.library.online_library_spring_app.dao.repository.BooksRepository;
import com.library.online_library_spring_app.dao.repository.EventRepository;
import com.library.online_library_spring_app.dao.repository.NotificationRepository;
import com.library.online_library_spring_app.dao.repository.UsersRepository;
import com.library.online_library_spring_app.dto.request.create.NotificationCreateRequest;
import com.library.online_library_spring_app.dto.response.NotificationResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.enums.NotificationMessage;
import com.library.online_library_spring_app.enums.NotificationStatus;
import com.library.online_library_spring_app.enums.ResponseCode;
import com.library.online_library_spring_app.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UsersRepository usersRepository;
    private final BooksRepository booksRepository;
    private final EventRepository eventRepository;

    public SuccessResponse<NotificationResponse> sendNotification(NotificationCreateRequest notificationCreateRequest) {
        Notification notification = notificationMapper.toNotification(notificationCreateRequest);
        NotificationResponse notificationResponse = buildNotificationResponse(notification, notificationCreateRequest);
        return SuccessResponse.createSuccessResponse(notificationResponse, ResponseCode.SUCCESS);
    }

    public SuccessResponse<NotificationResponse> deleteNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        notificationRepository.delete(notification);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<NotificationResponse>> getNotificationsByUser(Long userId) {
        List<NotificationResponse> notificationResponseList =
                notificationRepository.findNotificationByUsersId(userId)
                        .stream()
                        .map(notification -> {
                            NotificationResponse notificationResponse = new NotificationResponse();
                            String formattedMessage = "";
                            if (notification.getBooks() != null) {
                                formattedMessage = notification.getMessage()
                                        .formatMessageBook(notification.getBooks().getBookName());
                            } else if (notification.getEvent() != null) {
                                formattedMessage = notification.getMessage()
                                        .formatMessageEvent(notification.getEvent().getEventName(), notification.getEvent().getEventStartDate());
                            } else {
                                formattedMessage = "salam";
                            }
                            notificationResponse.setMessage(formattedMessage);
                            notificationResponse.setSentAt(notification.getSentAt());
                            notificationResponse.setType(notification.getType());
                            return notificationResponse;
                        })
                        .collect(Collectors.toList());
        return SuccessResponse.createSuccessResponse(notificationResponseList, ResponseCode.SUCCESS);
    }

    public NotificationResponse buildNotificationResponse(Notification notification, NotificationCreateRequest notificationCreateRequest) {
        Users users = usersRepository.findById(notificationCreateRequest.getUserId()).orElseThrow();
        notification.setUsers(users);
        System.out.println(users);

        Books books;
        Event event;
        String formattedMessage;
        if (notificationCreateRequest.getBookId() != null) {
            books = booksRepository.findById(notificationCreateRequest.getBookId()).orElseThrow();
            notification.setBooks(books);
            formattedMessage = notificationCreateRequest.getMessage().formatMessageBook(books.getBookName());
        } else {
            event = eventRepository.findById(notificationCreateRequest.getEventId()).orElseThrow();
            notification.setEvent(event);
            formattedMessage = notificationCreateRequest.getMessage().formatMessageEvent(event.getEventName(), String.valueOf(event.getEventStartDate()));
        }
        notificationRepository.save(notification);
        NotificationResponse notificationResponse = notificationMapper.toNotificationResponse(notification);
        notificationResponse.setMessage(formattedMessage);
        notificationResponse.setUser(users.getFirstName() + " " + users.getLastName());
        return notificationResponse;
    }
}
