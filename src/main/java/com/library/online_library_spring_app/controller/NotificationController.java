package com.library.online_library_spring_app.controller;

import com.library.online_library_spring_app.dto.request.create.NotificationCreateRequest;
import com.library.online_library_spring_app.dto.response.NotificationResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.service.NotificationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@SecurityRequirement(name = "Authorization")
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
