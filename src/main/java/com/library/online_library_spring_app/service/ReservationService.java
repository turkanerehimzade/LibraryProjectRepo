package com.library.online_library_spring_app.service;

import com.library.online_library_spring_app.dao.entity.Books;
import com.library.online_library_spring_app.dao.entity.Reservation;
import com.library.online_library_spring_app.dao.entity.Users;
import com.library.online_library_spring_app.dao.repository.BookReturnEventRepository;
import com.library.online_library_spring_app.dao.repository.BooksRepository;
import com.library.online_library_spring_app.dao.repository.ReservationRepository;
import com.library.online_library_spring_app.dao.repository.UsersRepository;
import com.library.online_library_spring_app.dto.request.ReservationCheckRequest;
import com.library.online_library_spring_app.dto.request.create.BookReturnEventCreateRequest;
import com.library.online_library_spring_app.dto.request.create.NotificationCreateRequest;
import com.library.online_library_spring_app.dto.request.create.ReservationCreateRequest;
import com.library.online_library_spring_app.dto.request.update.ReservationUpdateRequest;
import com.library.online_library_spring_app.dto.response.NotificationResponse;
import com.library.online_library_spring_app.dto.response.base.SuccessResponse;
import com.library.online_library_spring_app.dto.response.reservationResponse.ReservationBookResponse;
import com.library.online_library_spring_app.dto.response.reservationResponse.ReservationResponse;
import com.library.online_library_spring_app.dto.response.reservationResponse.ReservationUserResponse;
import com.library.online_library_spring_app.enums.NotificationMessage;
import com.library.online_library_spring_app.enums.NotificationType;
import com.library.online_library_spring_app.enums.ReservationStatus;
import com.library.online_library_spring_app.enums.ResponseCode;
import com.library.online_library_spring_app.mapper.BookReturnEventMapper;
import com.library.online_library_spring_app.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final BooksRepository booksRepository;
    private final NotificationService notificationService;
    private final BookReturnEventRepository bookReturnEventRepository;
    private final BookReturnEventMapper bookReturnEventMapper;

    public SuccessResponse<List<ReservationUserResponse>> getUserReservations(Long userId) {
        List<ReservationUserResponse> reservationUserResponseList = reservationRepository.findReservationsByUser_Id(userId).stream().map(reservationMapper::toReservationUserResponse).toList();
        return SuccessResponse.createSuccessResponse(reservationUserResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<ReservationBookResponse>> getBookReservations(String bookName) {
        List<ReservationBookResponse> reservationBookResponseList = reservationRepository.findReservationsByBook_BookName(bookName).stream().map(reservationMapper::toReservationBookResponse).toList();
        return SuccessResponse.createSuccessResponse(reservationBookResponseList, ResponseCode.SUCCESS);
    }

    public SuccessResponse<ReservationResponse> getReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        ReservationResponse reservationResponse = reservationMapper.toReservationResponse(reservation);
        return SuccessResponse.createSuccessResponse(reservationResponse, ResponseCode.SUCCESS);
    }

    public SuccessResponse<ReservationResponse> createReservation(ReservationCreateRequest reservationCreateRequest) {
        Reservation reservation = reservationMapper.toReservation(reservationCreateRequest);
        reservationRepository.save(reservation);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

    public SuccessResponse<ReservationResponse> cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        reservationRepository.save(reservation);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

    public SuccessResponse<ReservationResponse> updateReservation(Long reservationId, ReservationUpdateRequest reservationUpdateRequest) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        if (reservation.getStatus() != ReservationStatus.CANCELLED) {
            buildReservation(reservation, reservationUpdateRequest);
        }
        reservationRepository.save(reservation);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

    public void buildReservation(Reservation reservation, ReservationUpdateRequest reservationUpdateRequest) {
        if (reservationUpdateRequest.getReservationStart() != null) {
            reservation.setReservationStart(reservationUpdateRequest.getReservationStart());
        }
        if (reservationUpdateRequest.getReservationEnd() != null) {
            reservation.setReservationEnd(reservationUpdateRequest.getReservationEnd());
        }
    }

    public SuccessResponse<String> checkAvailability(ReservationCheckRequest reservationCheckRequest) {
        List<Reservation> reservations = reservationRepository.findReservationByBookId(reservationCheckRequest.getBookId());
        Books book = booksRepository.findById(reservationCheckRequest.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getCount() <= 0) {
            return SuccessResponse.createSuccessResponse("This book is currently not available in the library!", ResponseCode.SUCCESS);
        }

        for (Reservation reservation : reservations) {
            if (reservationCheckRequest.getCheckStartDate().isBefore(reservation.getReservationStart()) &&
                    reservationCheckRequest.getCheckEndDate().isAfter(reservation.getReservationEnd())) {
                return SuccessResponse.createSuccessResponse("This book is currently not available in the library!", ResponseCode.SUCCESS); // Kitab həmin tarixlərdə mövcud deyil
            }
        }
        return SuccessResponse.createSuccessResponse("This book is currently available in the library!", ResponseCode.SUCCESS);
    }

    public SuccessResponse<Object> updateBookStatus(Long userId, Long bookId) {
        Reservation reservation = reservationRepository.findReservationByUserIdAndBookId(userId, bookId).orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus(ReservationStatus.RETURNED);
        reservation.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        reservationRepository.save(reservation);
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

    public SuccessResponse<List<ReservationResponse>> checkOverdueBooks() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Reservation> reservations = reservationRepository.findAll();
        List<ReservationResponse> reservationResponseList = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (currentDate.isAfter(reservation.getReservationEnd()) && reservation.getStatus() == ReservationStatus.RESERVED) {
                ReservationResponse reservationResponse = reservationMapper.toReservationResponse(reservation);
                reservationResponse.setUserId(reservation.getUser().getId());
                reservationResponse.setBookId(reservation.getBook().getId());
                reservationResponseList.add(reservationResponse);
                return SuccessResponse.createSuccessResponse(reservationResponseList, ResponseCode.SUCCESS);
            }

        }
        return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
    }

    //todo is after yoxsa before
    public List<SuccessResponse<NotificationResponse>> sendOverdueNotices() {
        LocalDateTime currentDate = LocalDateTime.now();
        List<Reservation> reservations = reservationRepository.findAll();
        List<SuccessResponse<NotificationResponse>> notificationResponseList = new ArrayList<>();
        NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest();
        for (Reservation reservation : reservations) {
            if (currentDate.isAfter(reservation.getReservationEnd()) && reservation.getStatus() == ReservationStatus.RESERVED) {

                notificationCreateRequest.setMessage(NotificationMessage.REMINDER);
                notificationCreateRequest.setBookId(reservation.getBook().getId());
                notificationCreateRequest.setUserId(reservation.getUser().getId());
                notificationCreateRequest.setType(NotificationType.SMS);
                notificationCreateRequest.setSentAt(LocalDateTime.now());

                SuccessResponse<NotificationResponse> successResponse = notificationService.sendNotification(notificationCreateRequest);
                notificationResponseList.add(successResponse);
            }

        }
        return notificationResponseList;
    }

    public SuccessResponse<Object> logReturnEvent(BookReturnEventCreateRequest bookReturnEventCreateRequest) {
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            System.out.println("Reservation Book ID: " + reservation.getBook().getId());
            System.out.println("Reservation User ID: " + reservation.getUser().getId());
            if (reservation.getBook().getId().equals(bookReturnEventCreateRequest.getBookId())) {
                if (reservation.getUser().getId().equals(bookReturnEventCreateRequest.getUserId())) {
                    bookReturnEventRepository.save(bookReturnEventMapper.toBookReturnEvent(bookReturnEventCreateRequest));
                    return SuccessResponse.createSuccessResponse(null, ResponseCode.SUCCESS);
                }
            }

        }  return null;
    }//todo duzelt
}

