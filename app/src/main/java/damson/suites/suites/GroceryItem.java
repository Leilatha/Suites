package damson.suites.suites;

import java.io.Serializable;

/**
 * Created by Michael Chin on 5/7/2016.
 */
public class GroceryItem implements Serializable{

    private int id;
    private double price;
    private String item;
    private int quantity;


    public GroceryItem(double price, String item, int quantity) {
        id = -1;
        this.price = price;
        this.item = item;
        this.quantity = quantity;
    }

    public GroceryItem(Grocery item) {
        this.id = item.getId();
        this.price = item.getPrice();
        this.item = item.getName();
        this.quantity = item.getQuant();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice(){return price;}

    public String getItem(){
        return item;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setItem(String item){
        this.item = item;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
}
