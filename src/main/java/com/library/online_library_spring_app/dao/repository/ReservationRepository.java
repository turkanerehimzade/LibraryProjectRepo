package com.library.online_library_spring_app.dao.repository;

import com.library.online_library_spring_app.dao.entity.Books;
import com.library.online_library_spring_app.dao.entity.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findReservationsByUser_Id(Long userId);
    List<Reservation> findReservationsByBook_BookName(String bookName);
    List<Reservation>  findReservationByBookId(Long id);
    Optional<Reservation> findReservationByUserIdAndBookId(Long userId, Long id);
    @Query("SELECT r.book, COUNT(r.book) as rentalCount " +
            "FROM Reservation r " +
            "GROUP BY r.book " +
            "ORDER BY rentalCount DESC")
    List<Books> findMostReadBooks(Pageable pageable);
}
