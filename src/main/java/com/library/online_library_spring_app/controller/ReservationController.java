package com.library.online_library_spring_app.controller;

import com.library.online_library_spring_app.dto.request.create.ReservationCreateRequest;
import com.library.online_library_spring_app.dto.request.update.ReservationUpdateRequest;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.dto.response.reservationResponse.ReservationBookResponse;
import com.library.online_library_spring_app.dto.response.reservationResponse.ReservationResponse;
import com.library.online_library_spring_app.dto.response.reservationResponse.ReservationUserResponse;
import com.library.online_library_spring_app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//addReservation: +  ----- (null)İstifadəçi tərəfindən ---- //TODO bunu duzeltmelisen
//updateReservation: +
//cancelReservation: +
//getUserReservations: status +
//getBookReservations: +
//        getReservationDetails: + is active
//        checkAvailability: Kitabın müəyyən tarixlərdə mövcud olub olmadığını yoxlayır.
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/by-user/{userId}")
    private SuccessResponse<List<ReservationUserResponse>> getUserReservations(@PathVariable Long userId) {
        return reservationService.getUserReservations(userId);
    }
    @GetMapping("/by-book/{bookName}")
    private SuccessResponse<List<ReservationBookResponse>> getBookReservations(@PathVariable String bookName) {
        return reservationService.getBookReservations(bookName);
    }
    @GetMapping("/{id}")
    private SuccessResponse<ReservationResponse> getReservationDetails(@PathVariable Long id) {
        return reservationService.getReservation(id);
    }
    @PostMapping
    private SuccessResponse<ReservationResponse> addReservation(@RequestBody ReservationCreateRequest reservationCreateRequest) {
        return reservationService.createReservation(reservationCreateRequest);
    }
    @DeleteMapping("/{id}")
    private SuccessResponse<ReservationResponse> deleteReservation(@PathVariable Long id) {
        return reservationService.cancelReservation(id);
    }

    @PutMapping("/{id}")
    private SuccessResponse<ReservationResponse> updateReservation(@PathVariable Long id, @RequestBody ReservationUpdateRequest reservationUpdateRequest) {
        return reservationService.updateReservation(id,reservationUpdateRequest);
    }
}
