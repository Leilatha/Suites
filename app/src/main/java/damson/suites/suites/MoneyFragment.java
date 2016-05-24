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

    public static MoneyFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(STARTING_TEXT, text);

        MoneyFragment moneyFragment = new MoneyFragment();
        moneyFragment.setArguments(args);
        return moneyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(getArguments().getString(STARTING_TEXT));
        return textView;
    }
}
