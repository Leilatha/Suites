package damson.suites.suites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.net.URL;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_basket_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        item_field = (EditText) findViewById(R.id.Item_Text);
        quantity_field = (EditText) findViewById(R.id.Quantity_Text);
        price_field = (EditText) findViewById(R.id.Price_Text);

        //Button listener
        Button addButton = (Button)findViewById(R.id.Add_Button);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                attemptAddItem();
            }
        });

        Button cancelButton = (Button)findViewById(R.id.Cancel_Button);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                cancel();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "GroceryBasketAdd Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://damson.suites.suites/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
        */
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "GroceryBasketAdd Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://damson.suites.suites/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
        */
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

        //Store values at time of add attempt
        String name = item_field.getText().toString();
        int quantity = Integer.parseInt(quantity_field.getText().toString());
        double price = Double.parseDouble(price_field.getText().toString());

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
        if(quantity <= 0) {
            quantity_field.setError("Please fill in a quantity for the item");
            focusView = quantity_field;
            cancel = true;
        }

        //Check for valid price
        if(price < 0.0) {
            price_field.setError("This price is invalid. Please provide a positive value.");
            focusView = price_field;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }
        else {
            GroceryItem item = new GroceryItem(price, name, quantity);

            //need to upload item to database
            URL url = null;
            //get the information from Leon on how to add to database

            Intent intent = new Intent(this, GroceryBasket.class);
            setResult(RESULT_OK, intent);
            startActivity(intent);
        }

    }

    public void cancel(){
        Intent intent = new Intent(this, GroceryBasket.class);
        startActivity(intent);
    }
}
