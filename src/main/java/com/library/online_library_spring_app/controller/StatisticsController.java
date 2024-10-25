package com.library.online_library_spring_app.controller;

import com.library.online_library_spring_app.dto.response.BooksResponse;
import com.library.online_library_spring_app.dto.response.RentalHistoryResponse;
import com.library.online_library_spring_app.dto.response.UserActivityResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.service.BooksService;
import com.library.online_library_spring_app.service.UserActivityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
@SecurityRequirement(name = "Authorization")
public class StatisticsController {
    private final BooksService booksService;
    private final UserActivityService userActivityService;

    @GetMapping("/most-read")
    public SuccessResponse<List<BooksResponse>> getMostReadBooks(@RequestParam(defaultValue = "10") int limit) {
        return booksService.getMostReadBooks(limit);
    }

    @GetMapping("/rental/{id}")
    public SuccessResponse<List<RentalHistoryResponse>> getBookRentalHistory(@PathVariable("id") Long bookId) {
        return booksService.getBookRentalHistory(bookId);
    }

    @GetMapping("/user-activity/{id}")
    public SuccessResponse<UserActivityResponse> generateUserActivityReport(@PathVariable("id") Long userId) {
        return userActivityService.generateUserActivityReport(userId);
    }
}
