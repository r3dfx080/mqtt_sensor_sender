package com.client.mqttclient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class MainController {

    String message = "...";
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("message", message);
        return "home";
    }
}
