package com.jacson.demo.service;


import com.jacson.demo.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    
    List<Order> orderList = new ArrayList<>();


    public OrderService() {
        orderList.add(new Order("pratik", UUID.randomUUID().toString()));
        orderList.add(new Order("manthan", UUID.randomUUID().toString()));
        orderList.add(new Order("kharanshu", UUID.randomUUID().toString()));
        orderList.add(new Order("wanare", UUID.randomUUID().toString()));
    }
    
    public List<Order> getOrderList()
    {
        return orderList;
    }
}
