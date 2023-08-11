package com.example.ylswebsite.model.persistence;

import com.example.ylswebsite.model.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Long>, CustomRepository {
}
