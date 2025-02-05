package com.jacson.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
public class Order {

    private final String orderId;

    private final String customerName;

    public Order(Builder builder) {
        this.orderId = builder.orderId;
        this.customerName = builder.customerName;
    }


    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }


    public static class Builder {
        private String orderId;

        private String customerName;


        public Builder setOrderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder setCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
