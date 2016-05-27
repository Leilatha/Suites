package damson.suites.suites;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PSA.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PSA#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PSA extends Fragment {
    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    //private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private OnFragmentInteractionListener mListener;

    public PSA() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PSA.
     */
    // TODO: Rename and change types and number of parameters
    public static PSA newInstance() {
        PSA fragment = new PSA();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: fix with database stuff
        //List<String> psaList;
        //ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(
        //        this, android.R.layout.simple_expandable_list_item_2, psaList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_psa, container, false);
        ListView myList = (ListView) view.findViewById(R.id.listView);
        if (myList != null) {
            //myList.setAdapter(myAdapter);
        } else System.out.println("ERROR");
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override //TODO: MIGHT NEED TO UNCOMMENT THESE METHOD
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    /**public class SectionsPagerAdapter extends FragmentPagerAdapter {

     public SectionsPagerAdapter(FragmentManager fm) {
     super(fm);
     }

     @Override public Fragment getItem(int position) {
     // getItem is called to instantiate the fragment for the given page.
     // Return a PlaceholderFragment (defined as a static inner class below).
     return PlaceholderFragment.newInstance(position + 1);
     }
     }*/

        /* Written by Marian
     */
    public void buttonPress() {
        Intent receiveItemIntent = new Intent(getActivity(), GroceryBasketAdd.class);
        //setContentView(R.layout.activity_grocery_basket_add);
        startActivityForResult(receiveItemIntent, itemIdentifier);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Copied by Lexie
         * This creates an intent to the GroceryBasketAdd.java
         * It receives new items from that activity, and then
         * displays it into the list.
         */
        listMaker();
        final Button addButton = (Button) getView().findViewById(R.id.psa_button);
        if (addButton == null) {
            System.out.println("ERROR");
            return;
        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonPress();
            }
        });
    }

    /**
     * Lexie Rochfort
     * 5/7/16
     * takes the data to put in list
     */
    private void listMaker() {
        Suite.suite = new Suite(2, "qwert");
        DBHelper helper = new DBHelper(User.user.getEmail(), User.user.getPassword());
        helper.listSuiteGroceries(Suite.suite.getId(), new AsyncResponseHandler<DBGroceryListResult>() {
            @Override
            public void onSuccess(DBGroceryListResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                ListView myList = (ListView) getView().findViewById(R.id.grocery_basket_listView);

                myList.setVisibility(View.VISIBLE);

                // If no Items...
                if (response.getGroceryList() == null) {
                    myAdapter = null;
                    if (myList != null)
                        myList.setVisibility(View.GONE);
                    TextView tv = (TextView) getView().findViewById(R.id.noItemsView);
                    tv.setVisibility(View.VISIBLE);
                    System.out.println("NOTE: no items in myList");
                    return;
                }

                // There are items
                myAdapter = new GroceryAdapter(
                        getActivity(), (ArrayList<Grocery>) response.getGroceryList());
                if (myList != null) {
                    myList.setVisibility(View.VISIBLE);
                    myList.setAdapter(myAdapter);
                }
                else {
                    System.out.println("ERROR: myList not initialized");
                    return;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                FrameLayout frame = (FrameLayout) getView().findViewById(R.id.fragmentContainer);
                Snackbar
                        .make(frame, R.string.error_network_connection, Snackbar.LENGTH_LONG)
                        .show();
                System.out.println("ERROR: myList not initialized");
            }

            @Override
            public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                // TODO: Add "please log in again" code
                ListView myList = (ListView) getView().findViewById(R.id.grocery_basket_listView);
                if (myList != null)
                    myList.setVisibility(View.GONE);
                TextView tv = (TextView) getView().findViewById(R.id.noItemsView);
                tv.setVisibility(View.VISIBLE);
                System.out.println("ERROR: Not logged in.");
            }

            @Override
            public void onFinish(){
                final ListView myList = (ListView) getView().findViewById(R.id.grocery_basket_listView);

                myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(myList.getContext(), GroceryBasketEdit.class);
                        i.putExtra("item", (Serializable) myAdapter.getItem(position));
                        startActivity(i);
                    }
                });
            }
        });
    }
}
