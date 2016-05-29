package damson.suites.suites;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andy on 5/22/2016.
 */
public class GroceryAdapter extends ArrayAdapter<Grocery> {
    private static class ViewHolder {
        TextView GroceryName;
        TextView GroceryPrice;
        TextView GroceryQuantity;
    }

    public GroceryAdapter(Context context, ArrayList<Grocery> groceries) {
        super(context, R.layout.grocery_layout, R.id.GroceryName, groceries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Grocery grocery = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder = null;
        if (convertView == null) {
            // View Lookup stored in ViewHolder
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grocery_layout, parent, false);
            viewHolder.GroceryName = (TextView) convertView.findViewById(R.id.GroceryName);
            viewHolder.GroceryPrice = (TextView) convertView.findViewById(R.id.GroceryPrice);
            viewHolder.GroceryQuantity = (TextView) convertView.findViewById(R.id.GroceryQuantity);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.GroceryName.setText(grocery.getName());
        viewHolder.GroceryPrice.setText("$"+String.format("%.2f", grocery.getPrice()));
        viewHolder.GroceryQuantity.setText("Quantity: "+Integer.toString(grocery.getQuant()));
        viewHolder.GroceryName.setTextColor(Color.BLACK);
        viewHolder.GroceryPrice.setTextColor(Color.BLACK);
        viewHolder.GroceryQuantity.setTextColor(Color.BLACK);
        // Return the completed view to render on screen
        return convertView;
    }
}
