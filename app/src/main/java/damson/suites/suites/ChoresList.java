package damson.suites.suites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ChoresList extends AppCompatActivity {
    static final int itemIdentifier = 1;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_list);

        //TODO: fix with database stuff
        String [] choresList = listMaker();
        if(choresList[0].equals("")){
            System.out.println("ERROR");
            return;
        }
        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_expandable_list_item_1, choresList);
        ListView myList = (ListView) findViewById(R.id.listView);
        if(myList != null)
            myList.setAdapter(myAdapter);
        else {
            System.out.println("ERROR");
            return;
        }

        /* Written by Marian
         * This creates an intent to the ChoresAdd.java
         * It receives new items from that activity, and then
         * displays it into the list.
         */
        final Button addButton = (Button) findViewById(R.id.add_button);
        if(addButton == null){
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

    /* Written by Marian
     */
    public void buttonPress() {
        Intent receiveItemIntent = new Intent(this, ChoresAdd.class);
        setContentView(R.layout.activity_chores_add);
        startActivityForResult(receiveItemIntent, itemIdentifier);
    }

    /* Written by Marian
     * This is a continuation of the receiveItem method.
     * This is where we know if the add was successful. If it returned a
     * GroceryItem, then we can display its data to the list.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == itemIdentifier) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //  HOPEFULLY THIS ISNT A RUNTIME ERROR 8D
                GroceryItem newItem = (GroceryItem) data.getSerializableExtra("item_added");

                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
    }

    /**
     * Lexie Rochfort
     * 5/7/16
     * takes the data to put in list
     */
    private String[] listMaker(){
        String[] tempString = {"Dishes", "Trash", "Vacuuming"};
        return tempString;
    }
}
