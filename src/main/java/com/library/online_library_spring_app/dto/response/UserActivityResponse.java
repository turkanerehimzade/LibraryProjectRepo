package com.library.online_library_spring_app.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.library.online_library_spring_app.dao.entity.BookReturnEvent;
import com.library.online_library_spring_app.dao.entity.Reservation;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserActivityResponse {

    private String userName;
    private Timestamp creationTime;
    private List<ActivityDetails> activities;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ActivityDetails {

        private String bookName;
        private LocalDateTime reservationTime;
        private LocalDateTime returnTime;
    }
}

