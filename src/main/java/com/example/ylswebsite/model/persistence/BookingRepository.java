package com.example.ylswebsite.model.persistence;

import com.example.ylswebsite.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByDate(LocalDateTime appointment);
    List<Booking> findByLastName(String lastName);
    List<Booking> findByEmail(String email);
    @Query("SELECT COUNT(id) FROM Booking where email = :email")
    long countBookings(String email);
    Booking findById(long id);
}
