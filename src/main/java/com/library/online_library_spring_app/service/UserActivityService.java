package com.library.online_library_spring_app.service;

import com.library.online_library_spring_app.dao.entity.UserActivity;
import com.library.online_library_spring_app.dao.repository.UserActivityRepository;
import com.library.online_library_spring_app.dto.response.UserActivityResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.enums.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserActivityService {
    private final UserActivityRepository userActivityRepository;

    public SuccessResponse<UserActivityResponse> generateUserActivityReport(Long userId) {
        List<UserActivity> activities = userActivityRepository
                .findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserActivityResponse response = new UserActivityResponse();
        response.setCreationTime(activities.get(0).getUser().getCreatedAt());
        response.setUserName(activities.get(0).getUser().getUsername());
        List<UserActivityResponse.ActivityDetails> activityDetails = activities.stream().map(activity -> {
            UserActivityResponse.ActivityDetails details = new UserActivityResponse.ActivityDetails();
            details.setBookName(activity.getReservation().getBook().getBookName());
            details.setReservationTime(activity.getReservation().getReservationStart());
            details.setReturnTime(activity.getBookReturnEvent() != null ? activity.getBookReturnEvent().getReturnDate() : null);
            return details;
        }).collect(Collectors.toList());

        response.setActivities(activityDetails);

        return SuccessResponse.createSuccessResponse(response, ResponseCode.SUCCESS);
    }

}
