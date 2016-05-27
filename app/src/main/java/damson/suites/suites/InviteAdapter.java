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
public class InviteAdapter extends ArrayAdapter<Suite> {

    private static class ViewHolder {
        TextView InviteSuiteName;
        TextView InviteSuiteID;
    }

    public InviteAdapter(Context context, Suite[] invites) {
        super(context, R.layout.invite_layout, R.id.invite_suite_name, invites);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Suite suiteItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder = null;
        if (convertView == null) {
            // View Lookup stored in ViewHolder
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.invite_layout, parent, false);
            viewHolder.InviteSuiteName = (TextView) convertView.findViewById(R.id.invite_suite_name);
            viewHolder.InviteSuiteID = (TextView) convertView.findViewById(R.id.invite_suite_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.InviteSuiteName.setText(suiteItem.getName());
        viewHolder.InviteSuiteID.setText(Integer.toString(suiteItem.getId()));
        viewHolder.InviteSuiteName.setTextColor(Color.BLACK);
        viewHolder.InviteSuiteID.setTextColor(Color.BLACK);
        // Return the completed view to render on screen
        return convertView;
    }
}
