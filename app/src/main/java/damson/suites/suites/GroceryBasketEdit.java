package damson.suites.suites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GroceryBasketEdit extends AppCompatActivity {

    private EditText name;
    private EditText quantity;
    private EditText price;
    private String prevName;
    private Integer prevQuantity;
    private Double prevPrice;
    private Grocery itemDB;
    private GroceryItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_grocery_basket_edit);

        //get fields from item we are editing
        Intent i = getIntent();
        itemDB = (Grocery)i.getSerializableExtra("item");
        item = new GroceryItem(itemDB);

        name = (EditText) findViewById(R.id.grocery_basket_edit_Item_Text);
        quantity = (EditText) findViewById(R.id.grocery_basket_edit_Quantity_Text);
        price = (EditText) findViewById(R.id.grocery_basket_edit_Price_Text);

        //store previous information
        prevName = item.getItem();
        prevQuantity = item.getQuantity();
        prevPrice = item.getPrice();

        //fill fields with information
        name.setText(prevName);
        quantity.setText(Integer.toString(prevQuantity));
        price.setText(Double.toString(prevPrice));


        //set button listeners
        Button editButton = (Button)findViewById(R.id.grocery_basket_edit_Edit_Button);
        Button cancelButton = (Button) findViewById(R.id.grocery_basket_edit_Cancel_Button);
        editButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                attemptEdit();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                cancel();
            }
        });
    }

    private void attemptEdit() {
        //set errors to 0
        name.setError(null);
        quantity.setError(null);
        price.setError(null);

        //store values at time of edit attempt
        String newName = name.getText().toString();
        int newQuant = Integer.parseInt(quantity.getText().toString());
        double newPrice = Double.parseDouble(price.getText().toString());

        boolean cancel = false;
        boolean nameEdit = false;
        boolean quantEdit = false;
        boolean priceEdit = false;
        View focusView = null;

        //Check if fields were edited
        if(prevName.compareTo(newName) != 0){
            nameEdit = true;
        }
        if(prevQuantity.compareTo(new Integer(newQuant)) != 0){
            quantEdit = true;
        }
        if(prevPrice.compareTo(new Double(newPrice)) != 0){
            priceEdit = true;
        }

        //check if all fields were not edited
        if((nameEdit||quantEdit||priceEdit) == false){
            cancel = true;
            if(!nameEdit){;
                name.setError("You have not any fields. Please change a field or cancel editing.");
                focusView = name;
            }
            else if(!quantEdit){
                quantity.setError("You have not any fields. Please change a field or cancel editing.");
                focusView = quantity;
            }
            else if(!priceEdit){
                price.setError("You have not any fields. Please change a field or cancel editing.");
                focusView = price;
            }
        }

        if(cancel){
            focusView.requestFocus();
        }
        else{
            item.setItem(newName);
            item.setPrice(newPrice);
            item.setQuantity(newQuant);

            //upload change to database
            DBHelper help = new DBHelper(User.user);

            Intent intent = new Intent(this, GroceryBasket.class);
            setResult(RESULT_OK, intent);
            startActivity(intent);
        }
    }

    private void delete(){

    }

    private void cancel(){
        finish();
    }
}
