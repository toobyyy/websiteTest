package com.example.ylswebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/services")
public class ServiceController {
    @GetMapping()
    public String getServices(Model model) {
        return ("services");
    }
}
