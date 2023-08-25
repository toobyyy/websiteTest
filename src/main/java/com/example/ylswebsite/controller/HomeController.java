package com.example.ylswebsite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String Home(Model model) {
        model.addAttribute("greeting","Welkom");
        return ("index");
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
