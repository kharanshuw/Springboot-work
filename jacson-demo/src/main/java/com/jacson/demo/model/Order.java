package com.jacson.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {
    @JsonProperty("cname")
    private String customerName;
    
    @JsonProperty("customerid")
    private String id;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "customerName='" + customerName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
