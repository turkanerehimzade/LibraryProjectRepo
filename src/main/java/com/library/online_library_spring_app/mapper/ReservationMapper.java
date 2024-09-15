package com.library.online_library_spring_app.mapper;

import com.library.online_library_spring_app.dao.entity.Reservation;
import com.library.online_library_spring_app.dto.request.create.ReservationCreateRequest;
import com.library.online_library_spring_app.dto.response.reservationResponse.ReservationBookResponse;
import com.library.online_library_spring_app.dto.response.reservationResponse.ReservationResponse;
import com.library.online_library_spring_app.dto.response.reservationResponse.ReservationUserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationUserResponse toReservationUserResponse(Reservation reservation);
    ReservationBookResponse toReservationBookResponse(Reservation reservation);
    ReservationResponse toReservationResponse(Reservation reservation);
    Reservation toReservation(ReservationCreateRequest reservationCreateRequest);
}
