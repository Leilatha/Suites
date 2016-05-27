package damson.suites.suites;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Andy on 5/27/2016.
 */
public class InviteAdapter extends ArrayAdapter<DBInvitation> {

    public InviteAdapter(Context context, DBInvitation[] invites) {
        super(context, R.layout.invite_layout, R.id.invite_suite_name, invites);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        // Return the completed view to render on screen
        return convertView;
    }
}
