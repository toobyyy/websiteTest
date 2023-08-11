package com.example.ylswebsite.model.persistence;

import com.example.ylswebsite.model.Booking;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    List<Booking> findByDate(LocalDateTime appointment);
    List<Booking> findByLastName(String lastName);
    Booking findById(long id);
}
