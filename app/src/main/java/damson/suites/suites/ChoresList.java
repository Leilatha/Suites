package damson.suites.suites;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/* The majority of this is written by Marian Crofts, adapted from
 * the code from Grocery List. This file sets up buttons and
 * button listeners for the Chores List.
 */
public class ChoresList extends Fragment {
    static final int itemIdentifier = 1;  // The request code
    ArrayAdapter myAdapter;

    public ChoresList(){}

    public static ChoresList newInstance(){
        ChoresList fragment = new ChoresList();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chores_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listMaker();

        /* Written by Marian
         * This creates an intent to the ChoresAdd.java
         * It receives new items from that activity, and then
         * displays it into the list.
         */
        final Button addButton = (Button) getView().findViewById(R.id.chores_list_add_button);
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
        Intent receiveItemIntent = new Intent(getActivity(), ChoresAdd.class);
        //setContentView(R.layout.activity_chores_add);
        startActivityForResult(receiveItemIntent, itemIdentifier);
    }

    /**
     * Lexie Rochfort
     * 5/7/16
     * takes the data to put in list
     */
    private void listMaker(){
        //TODO make DB accessor class for Chores
        DBHelper helper = new DBHelper(User.user.getEmail(), User.user.getPassword());
        helper.listSuiteChores(Suite.suite.getId(), new AsyncResponseHandler<DBChoresListResult>() {
            @Override
            public void onSuccess(DBChoresListResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                View view = getView();
                if (view == null){
                    return;
                }
                if (view.getId() != R.id.chores_list_listView){
                    return;
                }
                ListView myList = (ListView) getView().findViewById(R.id.chores_list_listView);

                myList.setVisibility(View.VISIBLE);

                // If no Items...
                if (response.getChoreList() == null) {
                    myAdapter = null;
                    if (myList != null)
                        myList.setVisibility(View.GONE);
                    TextView tv = (TextView) getView().findViewById(R.id.noItemsView);
                    tv.setVisibility(View.VISIBLE);
                    System.out.println("NOTE: no items in myList");
                    return;
                }

                // There are items
                myAdapter = new ChoreAdapter(getActivity(), (ArrayList<DBChoreView>) response.getChoreList());
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
                View view = getView();
                if (view == null){
                    return;
                }
                if (view.getId() != R.id.chores_list_listView){
                    return;
                }
                FrameLayout frame = (FrameLayout) getView().findViewById(R.id.fragmentContainer);
                Snackbar
                        .make(frame, R.string.error_network_connection, Snackbar.LENGTH_LONG)
                        .show();
                System.out.println("ERROR: myList not initialized");
            }

            @Override
            public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                // TODO: Add "please log in again" code
                View view = getView();
                if(view == null){
                    return;
                }
                if(view.getId() != R.id.chores_list_listView) return;
                ListView myList = (ListView) getView().findViewById(R.id.chores_list_listView);
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
                if(view.getId() != R.id.chores_list_listView) return;
                final ListView myList = (ListView) getView().findViewById(R.id.chores_list_listView);

                myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(myList.getContext(), ChoresEdit.class);
                        i.putExtra("item", (Serializable) myAdapter.getItem(position));
                        startActivity(i);
                    }
                });
            }
        });
    }
}