package damson.suites.suites;


import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarFragment;
import com.roughike.bottombar.OnMenuTabSelectedListener;
import com.roughike.bottombar.OnTabSelectedListener;

/**
 * Created by Matthew on 5/21/2016.
 */
public class ThreeButtonsActivity extends FragmentActivity {
    private CoordinatorLayout coordinatorLayout;
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_buttons);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.three_buttons_activity);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer,
                new BottomBarFragment(MoneyFragment.newInstance(), R.drawable.money_management, "Money Manager"),
                new BottomBarFragment(PSA.newInstance(), R.drawable.psa, "PSA")
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

        mBottomBar.setOnItemSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onItemSelected(int position) {
                switch (position) {
                    case 0:
                        // Item 1 Selected
                }
            }
        });

        // Set the color for the active tab. Ignored on mobile when there are more than three tabs.
        mBottomBar.setActiveTabColor("#C2185B");

        // Use the dark theme. Ignored on mobile when there are more than three tabs.
        //bottomBar.useDarkTheme(true);

        // Use custom text appearance in tab titles.
        //bottomBar.setTextAppearance(R.style.MyTextAppearance);

        // Use custom typeface that's located at the "/src/main/assets" directory. If using with
        // custom text appearance, set the text appearance first.
        //bottomBar.setTypeFace("MyFont.ttf");
    }
}