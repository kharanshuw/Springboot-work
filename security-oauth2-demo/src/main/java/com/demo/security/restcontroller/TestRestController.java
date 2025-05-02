package com.demo.security.restcontroller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {
    
    @GetMapping("/")
    public String test()
    {
        return "running";
    }
}
