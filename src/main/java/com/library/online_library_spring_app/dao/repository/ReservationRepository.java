package com.library.online_library_spring_app.dao.repository;

import com.library.online_library_spring_app.dao.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findReservationsByUser_Id(Long userId);
    List<Reservation> findReservationsByBook_BookName(String bookName);
}
