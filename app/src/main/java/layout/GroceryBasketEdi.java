package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import damson.suites.suites.GroceryBasket;
import damson.suites.suites.GroceryItem;
import damson.suites.suites.R;

public class GroceryBasketEdi extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText name;
    private EditText price;
    private EditText quantity;

    ListView classListView = null;
    View view;


    public GroceryBasketEdi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroceryBasketEdi.
     */
    // TODO: Rename and change types and number of parameters
    public static GroceryBasketEdi newInstance(String param1, String param2) {
        GroceryBasketEdi fragment = new GroceryBasketEdi();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_grocery_basket_edi, container, false);
        classListView = (ListView) view .findViewById(R.id.fragment_grocery_basket);

        //get fields from item we are editing
        Intent intent = new Intent(super.getActivity(),GroceryBasket.class);
        GroceryItem item = (GroceryItem)intent.getSerializableExtra("item");

        name = (EditText) view.findViewById(R.id.Item_Text);
        quantity = (EditText) view.findViewById(R.id.Quantity_Text);
        price = (EditText) view.findViewById(R.id.Price_Text);

        //store previous information
        String prevName = item.getItem();
        String prevQuantity = item.getQuantity();
        Double prevPrice = item.getPrice();

        //fill fields with information
        name.setText(prevName);
        quantity.setText(prevQuantity);
        price.setText(Double.toString(prevPrice));


        /*set button listeners*/
        Button editButton = (Button)view.findViewById(R.id.Edit_Button);
        Button deleteButton = (Button)view.findViewById(R.id.Delete_Button);
        Button cancelButton = (Button)view.findViewById(R.id.Cancel_Button);

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

        return view;
    }

    private void attemptEdit() {

    }

    private void delete(){

    }

    private void cancel(){

    }

}
