package damson.suites.suites;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DBAddGroceryRequest {
    private final String name;
    private final int quantity;
    private final double price;

    @JsonCreator
    public DBAddGroceryRequest(@JsonProperty("name") String name,
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
