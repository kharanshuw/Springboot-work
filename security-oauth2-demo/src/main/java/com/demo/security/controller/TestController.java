package com.demo.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class TestController {

    
    @GetMapping("/login")
    public String loginpage() {
        return "login.html";
    }
    

    @GetMapping("/profile")
    public String getprofilepage() {
        return "profile";
    }
    
}   
