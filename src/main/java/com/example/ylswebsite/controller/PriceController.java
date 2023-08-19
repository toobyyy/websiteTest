package com.example.ylswebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/prices")
public class PriceController {

    @GetMapping()
    public String getPrices(Model model) {
        model.addAttribute("prices","Welkom");
        return ("prices");
    }
}
