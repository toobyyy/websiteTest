package com.example.ylswebsite.model.persistence;

import com.example.ylswebsite.model.Appointment;

import java.util.List;

public interface CustomRepository {
    public List<Appointment> findAllByEmail(String email);
}
