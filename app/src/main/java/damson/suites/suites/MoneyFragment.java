package damson.suites.suites;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Matthew on 5/23/2016.
 */
public class MoneyFragment extends Fragment{
    private static final String STARTING_TEXT = "Four Buttons Bottom Navigation";

    public MoneyFragment() {
    }

    public static MoneyFragment newInstance() {
        MoneyFragment fragment = new MoneyFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.money_management, container, false);
    }
}
