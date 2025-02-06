package com.jacson.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @JsonProperty("cname")
    private String customerName;
    
    @JsonProperty("customerid")
    private String id;

    

    
}
