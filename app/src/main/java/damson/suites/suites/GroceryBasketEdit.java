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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get fields from item we are editing
        Intent i = getIntent();
        GroceryItem item = (GroceryItem)i.getSerializableExtra("item");
        name = (EditText) findViewById(R.id.Item_Text);
        quantity = (EditText) findViewById(R.id.Quantity_Text);
        price = (EditText) findViewById(R.id.Price_Text);

        //store previous information
        String prevName = item.getItem();
        String prevQuantity = item.getQuantity();
        Double prevPrice = item.getPrice();

        //fill fields with information
        name.setText(prevName);
        quantity.setText(prevQuantity);
        price.setText(Double.toString(prevPrice));

        setContentView(R.layout.activity_grocery_basket_edit);

        //set button listeners
        Button editButton = (Button)findViewById(R.id.Edit_Button);
        Button deleteButton = (Button) findViewById(R.id.Delete_Button);
        Button cancelButton = (Button) findViewById(R.id.Cancel_Button);
        editButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                attemptEdit();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                delete();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                cancel();
            }
        });
    }

    private void attemptEdit() {

    }

    private void delete(){

    }

    private void cancel(){

    }
}
