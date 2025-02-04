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


    @GetMapping("/orders")
    public List<Order> getOrder() {
        logger.info("getorder executed");

        List<Order> list = orderService.getOrderList();

        logger.info("printing list message");
        for (Order order : list) {
            logger.info(order.toString());
        }
        return orderService.getOrderList();
    }
    
    
    /**public String getCurrentUser: This is a public method that returns a String.
    Principal principal: The Principal object represents the currently authenticated user. Spring Security injects this object into the method.
     */
    @GetMapping("/current-user")
    public String getCurrentUser(Principal principal)
    {
        String username = principal.getName().toString();
        logger.info("current user is"+username);
        return username;
    }
}
