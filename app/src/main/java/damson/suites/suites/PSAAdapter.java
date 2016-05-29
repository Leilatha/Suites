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
 * Copied by Lexie on 5/28/2016
 */
public class PSAAdapter extends ArrayAdapter<DBPSAView>{

    private static class ViewHolder{
        TextView AuthorName;
        TextView Date;
        TextView Text;
    }

    public PSAAdapter(Context context, ArrayList<DBPSAView> psa){
        super (context, R.layout.psa_layout, R.id.author, psa);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        DBPSAView psa = getItem(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.psa_layout, parent, false);
            viewHolder.AuthorName = (TextView) convertView.findViewById(R.id.author);
            viewHolder.Date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.Text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Populate data into template view using data object
        viewHolder.AuthorName.setText(psa.getAuthor().getName());
        String time = psa.getTimestamp().toString().substring(0, 16);
        viewHolder.Date.setText(time);
        viewHolder.Text.setText(psa.getDescription());
        viewHolder.AuthorName.setTextColor(Color.BLACK);
        viewHolder.Date.setTextColor(Color.BLACK);
        viewHolder.Text.setTextColor(Color.BLACK);
        return convertView;
    }
}
