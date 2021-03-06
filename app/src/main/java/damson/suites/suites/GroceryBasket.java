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
import android.os.Handler;
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
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.lang.Runnable;

import cz.msebera.android.httpclient.Header;
import com.roughike.bottombar.BottomBar;
import android.support.design.widget.CoordinatorLayout;

import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.OnMenuTabSelectedListener;


//change for commit
public class GroceryBasket extends Fragment {
    static final int itemIdentifier = 1;  // The request code
    private static final int GROCERY_EDIT = 3;
    ArrayAdapter myAdapter;

    final Handler handler = new Handler();
    private int position = 0;

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

        //listMaker();
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
        position = 0;
    }

    @Override
    public void onResume(){
        super.onResume();
        //startTimer();
        listMaker();
    }

    /*public void startTimer() {

        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms

        timer.schedule(timerTask, 5000, 10000); //

    } */

    /*public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listMaker();
                    }

                });

            }

        };

    }*/



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
        DBHelper helper = new DBHelper(User.user.getEmail(), User.user.getPassword());
        helper.listSuiteGroceries(Suite.suite.getId(), new AsyncResponseHandler<DBGroceryListResult>() {
            @Override
            public void onSuccess(DBGroceryListResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                View view = getView();
                if(view == null) return;
                if(view.getId() != R.id.grocery_basket_relative_layout) return;
                ListView myList = (ListView) view.findViewById(R.id.grocery_basket_listView);

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
                    TextView tv = (TextView) getView().findViewById(R.id.noItemsView);
                    tv.setVisibility(View.GONE);
                }
                else {
                    System.out.println("ERROR: myList not initialized");
                    return;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                View view = getView();
                if(view == null) return;
                if(view.getId() != R.id.grocery_basket_relative_layout) return;
                FrameLayout frame = (FrameLayout) view.findViewById(R.id.fragmentContainer);
                Snackbar
                        .make(frame, R.string.error_network_connection, Snackbar.LENGTH_LONG)
                        .show();
                System.out.println("ERROR: myList not initialized");
            }

            @Override
            public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                // TODO: Add "please log in again" code
                View view = getView();
                if(view == null) return;
                if(view.getId() != R.id.grocery_basket_relative_layout) return;
                ListView myList = (ListView) view.findViewById(R.id.grocery_basket_listView);
                if (myList != null)
                    myList.setVisibility(View.GONE);
                TextView tv = (TextView) getView().findViewById(R.id.noItemsView);
                tv.setVisibility(View.VISIBLE);
                System.out.println("ERROR: Not logged in.");
            }

            @Override
            public void onFinish(){
                View view = getView();
                if(view == null) return;
                if(view.getId() != R.id.grocery_basket_relative_layout) return;
                final ListView myList = (ListView) view.findViewById(R.id.grocery_basket_listView);
                myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(myList.getContext(), GroceryBasketEdit.class);
                        i.putExtra("item", (Serializable) myAdapter.getItem(position));
                        i.putExtra("position", position);
                        startActivityForResult(i, GROCERY_EDIT);
                    }
                });
                myList.setSelection(position);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        View view = getView();
        if(view == null) return;
        if(view.getId() != R.id.grocery_basket_relative_layout) return;
        if(requestCode == GROCERY_EDIT) {
            ListView myList = (ListView) view.findViewById(R.id.grocery_basket_listView);
            if(data != null) position = data.getIntExtra("position", 0);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}
