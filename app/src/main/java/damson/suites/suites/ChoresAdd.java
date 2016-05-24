package damson.suites.suites;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< .merge_file_a06128
import android.widget.EditText;
=======
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
>>>>>>> .merge_file_a11404

public class ChoresAdd extends Fragment {

<<<<<<< .merge_file_a06128
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
=======
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.money_management, container, false);

>>>>>>> .merge_file_a11404
    }
}
