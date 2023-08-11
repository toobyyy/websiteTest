package com.example.ylswebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/about-us")
public class AboutController {
    @GetMapping()
    public String getAboutUs(Model model) {
        return ("about-us");
    }
}
