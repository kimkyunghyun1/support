package com.example.support.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class firstController {

    @GetMapping("/hi")
    public String seeyou(Model model) {
        model.addAttribute("username", "good");
        return "wow";
    }
}
