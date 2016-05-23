package damson.suites.suites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ChoresAdd extends AppCompatActivity {

    private EditText nameField;
    private EditText quantityField;
    private EditText priceField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //create EditText objects
        nameField = (EditText) findViewById(R.id.Item_Text);
        quantityField = (EditText) findViewById(R.id.Quantity_Text);
        priceField = (EditText) findViewById(R.id.Price_Text);

        String prevName ;
        String prevQuantity;
        String prevPrice;

        setContentView(R.layout.activity_chores_add);
    }
}
