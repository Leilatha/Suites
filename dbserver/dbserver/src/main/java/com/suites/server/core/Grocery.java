package com.suites.server.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

public class Grocery {
    private final int id;
    private final String name;
    private final int quant;
    private final double price;

    @JsonCreator
    public Grocery(@JsonProperty("id") int id,
                   @JsonProperty("name") String name,
                   @JsonProperty("quantity") int quant,
                   @JsonProperty("price") double price) {
        this.id = id;
        this.name = name;
        this.quant = quant;
        this.price = price;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("quantity")
    public int getQuant() {
        return quant;
    }

    @JsonProperty("price")
    public double getPrice() {
        return price;
    }
}
