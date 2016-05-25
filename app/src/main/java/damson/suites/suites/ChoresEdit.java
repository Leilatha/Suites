package damson.suites.suites;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChoresEdit.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChoresEdit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoresEdit extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String name;
    private String description;

    private OnFragmentInteractionListener mListener;

    public ChoresEdit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChoresEdit.
     */
    // TODO: Rename and change types and number of parameters
    public static ChoresEdit newInstance(String param1, String param2) {
        ChoresEdit fragment = new ChoresEdit();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            description = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chores_edit, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

/*
package damson.suites.suites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChoresEdit extends AppCompatActivity {
    private EditText name;
    private EditText rotation;
    private EditText price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get fields from item we are editing
        Intent i = getIntent();
        GroceryItem item = (GroceryItem)i.getSerializableExtra("item");
        name = (EditText) findViewById(R.id.Item_Text);
        rotation = (EditText) findViewById(R.id.Quantity_Text);
        price = (EditText) findViewById(R.id.Price_Text);

        //store previous information
        String prevName = item.getItem();
        String prevQuantity = item.getQuantity();
        Double prevPrice = item.getPrice();

        //fill fields with information
        name.setText(prevName);
        rotation.setText(prevQuantity);
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
 */
