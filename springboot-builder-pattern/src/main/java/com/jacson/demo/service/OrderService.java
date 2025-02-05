package com.jacson.demo.service;


import com.jacson.demo.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    
    public Order getOrder()
    {
        Order order = new Order.Builder()
                .setOrderId("1").setCustomerName("pratik").build();
        
        return order;
    }
}
