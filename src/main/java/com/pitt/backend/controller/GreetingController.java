package com.pitt.backend.controller;
import com.pitt.backend.entity.ClinicalOrder;
import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {


    @MessageMapping("/newReminder")
    @SendTo("/topic/greetings")
    public ClinicalOrder greeting(ClinicalOrder reminder) throws Exception {
        reminder.setDes("sadasdsad");
        System.out.println("greeting");
        Thread.sleep(1000); // simulated delay
        return reminder;
    }

}
