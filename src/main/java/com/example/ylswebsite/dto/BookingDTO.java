package com.example.ylswebsite.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingDTO {
    private Long id;
    private LocalTime time;
    private LocalDate date;
    private String firstName;
    private String lastName;
    private String email;

    public BookingDTO() {}

    public BookingDTO(Long id, String firstName, String lastName, String email, LocalDate date, LocalTime time) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
