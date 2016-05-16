package com.suites.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class AddGroceryRequest {
    private final String name;
    private final int quantity;
    private final double price;

    @JsonCreator
    public AddGroceryRequest(@JsonProperty("name") String name,
                             @JsonProperty("quantity") int quantity,
                             @JsonProperty("price") double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("quantity")
    public int getQuantity() {
        return quantity;
    }

    @JsonProperty("price")
    public double getPrice() {
        return price;
    }
}
