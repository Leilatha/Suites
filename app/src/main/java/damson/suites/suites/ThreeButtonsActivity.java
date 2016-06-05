package damson.suites.suites;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarFragment;

import cz.msebera.android.httpclient.Header;
import layout.Calendar;

/**
 * Created by Matthew on 5/21/2016.
 */
public class ThreeButtonsActivity extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout;
    BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_buttons);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Eventually, set title to the name of the user's specific suite
        setTitle(Suite.suite.getName());

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.three_buttons_activity);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.noTopOffset();
        mBottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer,
                new BottomBarFragment(GroceryBasket.newInstance(), R.drawable.grocerieslist, "Groceries"),
                new BottomBarFragment(ChoresList.newInstance(), R.drawable.chores, "Chores"),
                new BottomBarFragment(PSAList.newInstance(), R.drawable.psa, "PSA"),
                new BottomBarFragment(MoneyFragment.newInstance(), R.drawable.moneymanagement, "Money Manager"),
                new BottomBarFragment(Calendar.newInstance(), R.drawable.calendar, "Calendar")


                //Last we need to add the chores fragment here

        );
        /*mBottomBar.setItemsFromMenu(R.menu.three_buttons_menu, new OnMenuTabSelectedListener() {
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
                        //public static PSA newInstance();
                }
            }
        }); */

        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
        mBottomBar.setActiveTabColor("#C2185B");

        // Use the dark theme. Ignored on mobile when there are more than three tabs.
        //bottomBar.useDarkTheme(true);

        // Use custom text appearance in tab titles.
        //bottomBar.setTextAppearance(R.style.MyTextAppearance);

        // Use custom typeface that's located at the "/src/main/assets" directory. If using with
        // custom text appearance, set the text appearance first.
        //bottomBar.setTypeFace("MyFont.ttf");

        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
        mBottomBar.mapColorForTab(3, "#FF5252");
        mBottomBar.mapColorForTab(4, "#FF9800");


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.invite_user:
                Intent i = new Intent(this, InviteUser.class);
                startActivity(i);
                break;
            case R.id.leave_suite:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Leave the suite?")
                        .setTitle("Confirm leave");
                builder.setPositiveButton("Leave", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper helper = new DBHelper(User.user);
                        helper.leaveSuite(Suite.suite.getId(), new AsyncResponseHandler<Void>() {
                            @Override
                            public void onSuccess(Void response, int statusCode, Header[] headers, byte[] errorResponse) {
                                Intent i = new Intent(getApplicationContext(), IntroActivity.class);
                                startActivity(i);
                                Suite.suite = null;
                                finish();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                                Toast.makeText(getApplicationContext(), "Leave Failed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.logout:
                User.user = null;
                Suite.suite = null;
                Intent j = new Intent(this, LoginActivity.class);
                finish();
                startActivity(j);
                break;
            //case R.id.
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);
    }
}