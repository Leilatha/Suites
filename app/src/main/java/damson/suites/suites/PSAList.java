package damson.suites.suites;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PSAList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PSAList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PSAList extends Fragment {
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
    static final int itemIdentifier = 1;
    private DBHelper helpMeAndy;

    ArrayAdapter myAdapter;

    private OnFragmentInteractionListener mListener;

    public PSAList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PSAList.
     */
    // TODO: Rename and change types and number of parameters
    public static PSAList newInstance() {
        PSAList fragment = new PSAList();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_psa);

        //TODO: fix with database stuff
        List<String> psaList;
        /**ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(
                this, android.R.layout.simple_expandable_list_item_2, psaList);*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_psa, container, false);
        ListView myList = (ListView) view.findViewById(R.id.listView);
        if (myList != null) {
            myList.setAdapter(myAdapter);
        } else System.out.println("ERROR");
        // Inflate the layout for this fragment
        return view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_psa_add, null))
                // Add action buttons
                .setPositiveButton(R.string.psa_add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // add a psa
                    }
                })
                .setNegativeButton(R.string.psa_add_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //LoginDialogFragment.this.getDialog().cancel();
                        //This is copy pasted so might need to fix what doesn't make sense.
                    }
                });
        return builder.create();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override TODO: MIGHT NEED TO UNCOMMENT THESE METHOD
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
    */

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

    /* poorly copied by Marian
     */
    public void buttonPress() {
        if(((TextView) getView().findViewById(R.id.psaText)).getText().toString().equals("")){
            return;
        }
        helpMeAndy = new DBHelper(User.user);
        helpMeAndy.postSuitePSA(Suite.suite.getId(),
                new String(""),
                ((TextView) getView().findViewById(R.id.psaText)).getText().toString(),
                new AsyncResponseHandler<DBGenericResult>() {
                    @Override
                    public void onSuccess(DBGenericResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                        ((TextView) getView().findViewById(R.id.psaText)).setText("");
                        listMaker();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(R.string.PostMessageWan)
                                .setTitle(R.string.PostError);
                        AlertDialog dialog = builder.create();
                    }

                    @Override
                    public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(R.string.PostMessageTuu)
                                .setTitle(R.string.PostError);
                        AlertDialog dialog = builder.create();                    }
                });


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
        DBHelper helper = new DBHelper(User.user.getEmail(), User.user.getPassword());
        //TODO: fix Suite.suite if doesn't work
        helper.listSuitePSA(Suite.suite.getId(), new AsyncResponseHandler<DBPSAListResult>() {
            @Override
            public void onSuccess(DBPSAListResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                View view = getView();
                if(view == null){
                    return;
                }
                if (view.getId() != R.id.psaFrame){
                    return;
                }
                ListView myList = (ListView) getView().findViewById(R.id.psa_ListView);

                myList.setVisibility(View.VISIBLE);

                // If no Items...
                if (response.getPSAList() == null) {
                    myAdapter = null;
                    if (myList != null)
                        myList.setVisibility(View.GONE);
                    TextView tv = (TextView) getView().findViewById(R.id.noItemsView);
                    tv.setVisibility(View.VISIBLE);
                    System.err.println("NOTE: no items in PSAList");
                    return;
                }

                // There are items
                myAdapter = new PSAAdapter(
                        getActivity(), (ArrayList<DBPSAView>) response.getPSAList());
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
                if(view == null){
                    return;
                }
                if (view.getId() != R.id.psaFrame){
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
                if (view.getId() != R.id.psaFrame){
                    return;
                }
                ListView myList = (ListView) getView().findViewById(R.id.psa_ListView);
                if (myList != null)
                    myList.setVisibility(View.GONE);
                TextView tv = (TextView) getView().findViewById(R.id.noItemsView);
                tv.setVisibility(View.VISIBLE);
                System.out.println("ERROR: Not logged in.");
            }

            @Override
            public void onFinish(){
                View view = getView();
                if(view == null){
                    return;
                }
                if (view.getId() != R.id.psaFrame){
                    return;
                }
                final ListView myList = (ListView) getView().findViewById(R.id.psa_ListView);

                myList.setSelection(myList.getCount()-1);
            }
        });
    }
}
