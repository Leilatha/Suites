package damson.suites.suites;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Michael Chin on 5/27/2016.
    */
public class ChoreAdapter extends ArrayAdapter<DBChoreView>{

    private static class ViewHolder{
        TextView ChoreName;
        TextView ChoreDescription;
        TextView ChoreCurrentTurn;
    }

    public ChoreAdapter(Context context, ArrayList<DBChoreView> chores){
        super (context, R.layout.chore_layout, R.id.ChoreName, chores);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        DBChoreView chore = getItem(position);
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chore_layout, parent, false);
            viewHolder.ChoreName = (TextView) convertView.findViewById(R.id.ChoreName);
            viewHolder.ChoreDescription = (TextView) convertView.findViewById(R.id.ChoreDescription);
            viewHolder.ChoreCurrentTurn = (TextView) convertView.findViewById(R.id.ChoreCurrentTurn);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Populate data into template view using data object
        viewHolder.ChoreName.setText(chore.getName());
        viewHolder.ChoreDescription.setText(chore.getDescription());
        viewHolder.ChoreCurrentTurn.setText(chore.getAssignees().get(chore.getCurrentTurn()).getName());
        viewHolder.ChoreName.setTextColor(Color.BLACK);
        viewHolder.ChoreDescription.setTextColor(Color.BLACK);
        viewHolder.ChoreCurrentTurn.setTextColor(Color.BLACK);
        return convertView;
    }
}
