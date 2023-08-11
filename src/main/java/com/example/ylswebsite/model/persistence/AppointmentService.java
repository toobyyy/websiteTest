package com.example.ylswebsite.model.persistence;

import com.example.ylswebsite.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AppointmentService implements CustomRepository {
    @Autowired
    private JdbcTemplate jtm;
    public List<Appointment> findAllByEmail(String email) {
        String sql="select * from Appointment where email="+email;
        return jtm.query(sql, new BeanPropertyRowMapper<>(Appointment.class));
    }
}
