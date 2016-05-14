package damson.suites.suites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.net.URL;

public class GroceryBasketAdd extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_basket_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
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
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
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
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /**
     * This method creates a GroceyItem object to be sent to GroceryBaseket. It should be able to
     * upload the item to the structure holding the rest of the GroceryItem objects, and then
     * display the list.
     *
     * Author: Michael Chin
     */
    public void addItem(View view) {
        EditText editText = (EditText) findViewById(R.id.Item_Text); //item name
        EditText editText2 = (EditText) findViewById(R.id.Quantity_Text); //item quantity
        EditText editText3 = (EditText) findViewById(R.id.Price_Text); //item price
        String name = editText.getText().toString();
        String quantity = editText2.getText().toString();
        double price = Double.parseDouble(editText3.getText().toString());
        GroceryItem item = new GroceryItem(price, name, quantity);

        //need to upload item to database
        URL url = null;


        Intent intent  = new Intent();

        setResult(RESULT_OK, intent);
        /* In the GroceryBasket class, first check if the intent sent anything.
         * Use the following lines in onStart():
         * Intent NAME = getIntent();
         * GroceryItem item;
         * if(NAME.getSerializable() != null)[
         *      item = (GroceryItem) i.getSerializable("item_added");
         * }
         *
         * after, do null check on item. If it isn't null, send the item to the database to be
         * added to the database, then display all grocery items available in database.
         */
    }

    public void cancel(View view){
        Intent intent = new Intent(this, GroceryBasket.class);
        startActivity(intent);
    }
}
