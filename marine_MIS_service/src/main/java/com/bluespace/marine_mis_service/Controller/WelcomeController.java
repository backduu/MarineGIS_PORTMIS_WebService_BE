package com.bluespace.marine_mis_service.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/wecome")
    public String welcome(){
        return "Welcome to Marine MIS Service";
    }
}
