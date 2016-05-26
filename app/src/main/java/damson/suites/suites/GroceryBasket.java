package damson.suites.suites;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;

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

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import com.roughike.bottombar.BottomBar;
import android.support.design.widget.CoordinatorLayout;

import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class GroceryBasket extends Fragment {
    static final int itemIdentifier = 1;  // The request code
    ArrayAdapter myAdapter;

    public GroceryBasket()
    {}

    public static GroceryBasket newInstance() {
        GroceryBasket fragment = new GroceryBasket();
        return fragment;
    }

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grocery_basket, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_grocery_basket);

        //TODO: fix with database stuff

        listMaker();
        //BottomBar mBottomBar = BottomBar.attach(this, savedInstanceState);
        //mBottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer,
            //new BottomBarFragment(PSA.newInstance(), R.drawable.psa, "PSA")
        //);

        /* mBottomBar.setItemsFromMenu(R.menu.three_buttons_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
                switch (itemId) {
                    case R.id.chores:
                        //Snackbar.make(coordinatorLayout, "Recent Item Selected", Snackbar.LENGTH_LONG).show();
                        break;
                    case R.id.money:
                        //Snackbar.make(coordinatorLayout, "Favorite Item Selected", Snackbar.LENGTH_LONG).show();
                        break;
                    case R.id.groceries_list:
                        //Snackbar.make(coordinatorLayout, "Location Item Selected", Snackbar.LENGTH_LONG).show();
                        break;
                    case R.id.psa:
                        public static PSA newInstance();
                }
            }
        }); */


        /* if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        else {
            System.out.println("ERROR: getSupportActionBar does not exist");
            return;
        } */
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        //mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        /*mViewPager = (ViewPager) findViewById(R.id.container);
        if(mViewPager != null)
            mViewPager.setAdapter(mSectionsPagerAdapter);
        else {
            System.out.println("ERROR");
            return;
        } */

        /* Written by Marian
         * This creates an intent to the GroceryBasketAdd.java
         * It receives new items from that activity, and then
         * displays it into the list.
         */
        final Button addButton = (Button) getView().findViewById(R.id.grocery_basket_add_button);
        if(addButton == null){
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

    @Override
    public void onResume(){
        super.onResume();
        listMaker();
    }

    /* Written by Marian
     */
    public void buttonPress() {
        Intent receiveItemIntent = new Intent(getActivity(), GroceryBasketAdd.class);
        //setContentView(R.layout.activity_grocery_basket_add);
        startActivityForResult(receiveItemIntent, itemIdentifier);
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


    @Override
    public void onStop() {
        super.onStop();
    }


}
