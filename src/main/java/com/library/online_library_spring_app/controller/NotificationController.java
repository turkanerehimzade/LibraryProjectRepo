package com.library.online_library_spring_app.controller;

import com.library.online_library_spring_app.dto.request.create.NotificationCreateRequest;
import com.library.online_library_spring_app.dto.response.NotificationResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//getUpcomingReminders //TODO QALIB BUTOV
//sendReminderNotification
//Görəcəyi İş: İstifadəçiyə e-poçt, SMS və ya tətbiq içi bildiriş vasitəsilə xatırlatma göndərir.
//Giriş Parametrləri: userId, message
//        addNewBookNotification
//Görəcəyi İş: İstifadəçilərə yeni kitablar haqqında bildiriş əlavə edir.
//Giriş Parametrləri: userId, bookId, notificationMessage
//        addEventNotification
//Görəcəyi İş: İstifadəçilərə tədbirlər haqqında bildiriş əlavə edir.
//Giriş Parametrləri: userId, eventId, notificationMessage
//        getNotificationsByUser
//Görəcəyi İş: Müəyyən istifadəçi üçün bütün bildirişləri əldə edir.
//Giriş Parametrləri: userId
//        removeNotification:+

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;
    @PostMapping
    public SuccessResponse<NotificationResponse> sendNotification(@RequestBody NotificationCreateRequest notificationCreateRequest) {
        return notificationService.sendNotification(notificationCreateRequest);
    }
    @DeleteMapping("/{id}")
    public SuccessResponse<NotificationResponse> removeNotification(@PathVariable("id") Long id) {
        return notificationService.deleteNotification(id);
    }
    @GetMapping("/{id}")
    public SuccessResponse<List<NotificationResponse>>  getNotificationsByUser(@PathVariable("id") Long id) {
        return notificationService.getNotificationsByUser(id);
    }
}
