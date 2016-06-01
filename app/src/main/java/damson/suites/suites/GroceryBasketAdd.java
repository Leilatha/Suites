package damson.suites.suites;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cz.msebera.android.httpclient.Header;

public class GroceryBasketAdd extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;
    //UI references
    private EditText item_field;
    private EditText quantity_field;
    private EditText price_field;
    GroceryItem groceryitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_basket_add);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.grocery_basket_add_toolbar);
        //setSupportActionBar(toolbar);

        item_field = (EditText) findViewById(R.id.grocery_basket_add_item_text);
        quantity_field = (EditText) findViewById(R.id.grocery_basket_add_quantity_text);
        price_field = (EditText) findViewById(R.id.grocery_basket_add_price_text);

        //Button listener
        Button addButton = (Button)findViewById(R.id.grocery_basket_add_add_button);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                attemptAddItem();
            }
        });

        Button cancelButton = (Button)findViewById(R.id.grocery_basket_edit_Cancel_Button);
        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                cancel();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * This method creates a GroceyItem object to be uploaded to the database.
     *
     * Author: Michael Chin
     */
    public void attemptAddItem() {
        //reset errors
        item_field.setError(null);
        quantity_field.setError(null);
        price_field.setError(null);

        String name;
        String quantity;
        double price;

        //Store values at time of add attempt
        if(item_field.getText().toString().length() != 0) {
            name = item_field.getText().toString();
        }
        else{
            name = "";
        }
        if(quantity_field.getText().toString().length() != 0) {
            quantity = quantity_field.getText().toString();
        }
        else{
            quantity = "";
        }
        if(price_field.getText().toString().length() != 0) {
            price = Double.parseDouble(price_field.getText().toString());
        }
        else{
            price = 0.0;
        }

        boolean cancel = false;
        View focusView = null;

        //Check for valid item name
        //Assumes user puts in a name. Error only if empty
        if(TextUtils.isEmpty(name)) {
            item_field.setError("Please fill in the name of the item");
            focusView = item_field;
            cancel = true;
        }

        //Check for valid quantity
        //Assumes user provides quantity in the correct format. Error only if empty
        if(TextUtils.isEmpty(quantity)) {
            quantity_field.setError("Please fill in a quantity for the item");
            focusView = quantity_field;
            cancel = true;
        }

        //Check for valid price
        if(price == 0.0) {
            price_field.setError("Please fill in a price for the item.");
            focusView = price_field;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }
        else {
            groceryitem = new GroceryItem(price, name, Integer.parseInt(quantity));
            DBAddGroceryRequest item = new DBAddGroceryRequest(name, Integer.parseInt(quantity), price);

            // Upload item to grocery basket
            DBHelper helper = new DBHelper(User.user.getEmail(), User.user.getPassword());
            helper.addGroceryToSuite(Suite.suite.getId(), item, new AsyncResponseHandler<DBGenericResult>() {
                @Override
                public void onSuccess(DBGenericResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.grocery_basket_add_coordinator),
                            R.string.error_grocery_add_failed_no_permission, Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    Log.e("GroceryBasketAdd", "Not in suite.");
                }

                @Override
                public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                    // TODO: Set up a "please login again" page
                }
            });
            //get the information from Leon on how to add to database
        }

    }

    public void cancel(){
        finish();
    }
}
