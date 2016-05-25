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

public class GroceryBasket extends AppCompatActivity {
    static final int itemIdentifier = 1;  // The request code
    ArrayAdapter myAdapter;

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_grocery_basket);

        //TODO: fix with database stuff

        listMaker();
        BottomBar mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer,
            new BottomBarFragment(PSA.newInstance(), R.drawable.psa, "PSA")
        );

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
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

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
        final Button addButton = (Button) findViewById(R.id.grocery_basket_add_button);
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

    /* Written by Marian
     */
    public void buttonPress() {
        System.out.println("I MADE IT TO BUTTON PRESSSSSSSSSSSS");
        Intent receiveItemIntent = new Intent(this, GroceryBasketAdd.class);
        //setContentView(R.layout.activity_grocery_basket_add);
        startActivityForResult(receiveItemIntent, itemIdentifier);
    }

    /**
     * Lexie Rochfort
     * 5/7/16
     * takes the data to put in list
     */
    private void listMaker() {
        //TODO: Remove this. It is for testing
        User.user = new User(123, "user", "Qwerty", "qwerty");
        User.user.setPassword("pass");
        Suite.suite = new Suite(2, "qwert");

        DBHelper helper = new DBHelper(User.user.getEmail(), User.user.getPassword());
        helper.listSuiteGroceries(Suite.suite.getId(), new AsyncResponseHandler<DBGroceryListResult>() {
            @Override
            public void onSuccess(DBGroceryListResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                ListView myList = (ListView) findViewById(R.id.grocery_basket_listView);

                myList.setVisibility(View.VISIBLE);

                // If no Items...
                if (response.getGroceryList() == null) {
                    myAdapter = null;
                    if (myList != null)
                        myList.setVisibility(View.GONE);
                    TextView tv = (TextView) findViewById(R.id.noItemsView);
                    tv.setVisibility(View.VISIBLE);
                    System.out.println("NOTE: no items in myList");
                    return;
                }

                // There are items
                myAdapter = new GroceryAdapter(
                        getApplicationContext(), (ArrayList<Grocery>) response.getGroceryList());
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
                FrameLayout frame = (FrameLayout) findViewById(R.id.fragmentContainer);
                Snackbar
                        .make(frame, R.string.error_network_connection, Snackbar.LENGTH_LONG)
                        .show();
                System.out.println("ERROR: myList not initialized");
            }

            @Override
            public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                // TODO: Add "please log in again" code
                ListView myList = (ListView) findViewById(R.id.grocery_basket_listView);
                if (myList != null)
                    myList.setVisibility(View.GONE);
                TextView tv = (TextView) findViewById(R.id.noItemsView);
                tv.setVisibility(View.VISIBLE);
                System.out.println("ERROR: Not logged in.");
            }

            @Override
            public void onFinish(){
                final ListView myList = (ListView) findViewById(R.id.grocery_basket_listView);

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
    public void onRestart() {
        super.onRestart();
        listMaker();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_grocery_basket, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    /**
     * Lexie Rochfort
     * 5/7/2016
     */
    private class dataHandler {
        //pull info from database
        //put into array of arrays
        //make array of arrays into list items
        //go through array and make  list item out of

    }
}
