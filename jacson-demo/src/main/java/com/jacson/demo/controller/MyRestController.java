package com.jacson.demo.controller;

import com.jacson.demo.model.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {
    
    @PostMapping("/getorder")
    public String getOrder(@RequestBody Order order)
    {
        return order.toString();
    }
    
}
