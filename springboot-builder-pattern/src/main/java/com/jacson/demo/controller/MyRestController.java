package com.jacson.demo.controller;


import com.jacson.demo.model.Order;
import com.jacson.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.security.Principal;
import java.util.List;

@RestController
public class MyRestController {

    private static final Logger logger
            = LoggerFactory.getLogger(MyRestController.class);

    private OrderService orderService;

    @Autowired
    public MyRestController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/getorder")
    public String getOrder() {
        Order order = orderService.getOrder();
        return order.toString();
    }
}
