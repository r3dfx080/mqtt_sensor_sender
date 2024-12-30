package com.client.mqttclient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final MqttService mqttService;
    String status;

    public MainController(MqttService mqttService) {
        this.mqttService = mqttService;
        status = "Start sending values";
    }


    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("status", status);
        return "index";
    }

    @PostMapping("/action1")
    public String publishMessage(@RequestParam String topic) {
        mqttService.publishMessage();
        return "Message published to topic: " + topic;
    }

    @PostMapping("/action2")
    public String publishExtraMessage(@RequestParam String topic) {
        mqttService.publishMessage();
        return "Message published to topic: " + topic;
    }

    //Ставим на поток publishMessage + добавляем Рандом + меняем параметры (топик, юрл)
}
