package com.example.ylswebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/afspraak")
public class AppointmentController {
    @GetMapping
    public String getAppointment() {
        return "booking";
    }
}
