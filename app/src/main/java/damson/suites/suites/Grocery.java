package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Grocery implements Serializable {
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

    public Grocery(GroceryItem groc) {
        id = groc.getId();
        name = groc.getItem();
        quant = groc.getQuantity();
        price = groc.getPrice();
    }

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

    @Override
    public String toString() {
        return name;
    }
}
