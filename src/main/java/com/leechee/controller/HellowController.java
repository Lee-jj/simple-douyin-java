package com.leechee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HellowController {
    
    @GetMapping("/hello")
    public String hello() {
        System.out.println("terminal test.");
        return "Test success.";
    }
}
