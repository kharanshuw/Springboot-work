package com.example.demo.controller;

import com.example.demo.model.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class FirstController {

    Logger logger
            = LoggerFactory.getLogger(FirstController.class);

    @PostMapping("/getorder")
    public String getOrder(@RequestBody Order order) {
        String message = "hello curstomer " + order.getCurstomerName() + "having id " + order.getCustomerId();
        logger.info( "hello curstomer " + order.getCurstomerName() + "having id " + order.getCustomerId());
        return message;
    }
}
