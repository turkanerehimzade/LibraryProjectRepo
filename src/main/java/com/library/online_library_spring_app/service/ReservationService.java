package com.library.online_library_spring_app.service;

import com.library.online_library_spring_app.dao.entity.Reservation;
import com.library.online_library_spring_app.dao.entity.Users;
import com.library.online_library_spring_app.dao.repository.ReservationRepository;
import com.library.online_library_spring_app.dto.request.create.ReservationCreateRequest;
import com.library.online_library_spring_app.dto.request.update.ReservationUpdateRequest;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.dto.response.reservationResponse.ReservationBookResponse;
import com.library.online_library_spring_app.dto.response.reservationResponse.ReservationResponse;
import com.library.online_library_spring_app.dto.response.reservationResponse.ReservationUserResponse;
import com.library.online_library_spring_app.enums.ReservationStatus;
import com.library.online_library_spring_app.enums.ResponseCode;
import com.library.online_library_spring_app.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    public SuccessResponse<List<ReservationUserResponse>> getUserReservations(Long userId) {
       List<ReservationUserResponse> reservationUserResponseList=  reservationRepository.findReservationsByUser_Id(userId).stream().map(reservationMapper::toReservationUserResponse).toList();
        return SuccessResponse.createSuccessResponse(reservationUserResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<ReservationBookResponse>> getBookReservations(String bookName){
        List<ReservationBookResponse> reservationBookResponseList=reservationRepository.findReservationsByBook_BookName(bookName).stream().map(reservationMapper::toReservationBookResponse).toList();
        return SuccessResponse.createSuccessResponse(reservationBookResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<ReservationResponse> getReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        ReservationResponse reservationResponse = reservationMapper.toReservationResponse(reservation);
        return SuccessResponse.createSuccessResponse(reservationResponse, ResponseCode.SUCCESS);
    }
    public SuccessResponse<ReservationResponse> createReservation(ReservationCreateRequest reservationCreateRequest) {
        Reservation reservation=reservationMapper.toReservation(reservationCreateRequest);
        reservationRepository.save(reservation);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }
    public SuccessResponse<ReservationResponse> cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }
    public SuccessResponse<ReservationResponse> updateReservation(Long reservationId, ReservationUpdateRequest reservationUpdateRequest) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        if(reservation.getStatus()!=ReservationStatus.CANCELLED){
            buildReservation(reservation,reservationUpdateRequest);
        }
        reservationRepository.save(reservation);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }
    public void buildReservation(Reservation reservation,ReservationUpdateRequest reservationUpdateRequest) {
        if(reservationUpdateRequest.getReservationStart()!=null){
            reservation.setReservationStart(reservationUpdateRequest.getReservationStart());
        }
        if(reservationUpdateRequest.getReservationEnd()!=null){
            reservation.setReservationEnd(reservationUpdateRequest.getReservationEnd());
        }
    }
}

