package damson.suites.suites;

import java.io.Serializable;

/**
 * Created by Michael Chin on 5/7/2016.
 */
public class GroceryItem implements Serializable{

    private double price;
    private String item;
    private String quantity;


    public GroceryItem(double price, String item, String quantity) {
        this.price = price;
        this.item = item;
        this.quantity = quantity;
    }

    public double getPrice(){
        return price;
    }

    public String getItem(){
        return item;
    }

    public String getQuantity(){
        return quantity;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setItem(String item){
        this.item = item;
    }

    public void setQuantity(String quantity){
        this.quantity = quantity;
    }
}
