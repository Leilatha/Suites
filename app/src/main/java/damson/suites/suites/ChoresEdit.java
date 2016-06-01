package damson.suites.suites;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class ChoresEdit extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private String prevName;
    private String prevDescription;
    private DBChoreView chore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chores_edit);

        //get fields from item we're editing
        Intent i = getIntent();
        try {
            chore = DBHelper.mapper.readValue(i.getStringExtra("item"), DBChoreView.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        prevName = chore.getName();
        prevDescription = chore.getDescription();

        name = (EditText) findViewById(R.id.chores_list_edit_Chore_Text);
        description = (EditText) findViewById(R.id.chores_list_edit_Description_text);

        //fill fields
        name.setText(prevName);
        description.setText(prevDescription);

        //set button listeners
        Button editButton = (Button)findViewById(R.id.chores_list_edit_Edit_button);
        Button cancelButton = (Button)findViewById(R.id.chores_list_edit_Cancel_button);
        Button deleteButton = (Button)findViewById(R.id.chores_list_edit_Delete_button);
        Button randomizeButton = (Button)findViewById(R.id.chores_list_edit_Randomize_button);
        editButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                attemptEdit();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                cancel();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                delete();
            }
        });
        randomizeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                chore.randomize();
                attemptEdit();
                sendRandomize();
                finish();
            }
        });
    }

    private void attemptEdit(){
        //set errors to null
        name.setError(null);
        description.setError(null);

        //store values at time of edit attempt
        String newName = name.getText().toString();
        String newDescript = description.getText().toString();

        boolean cancel = false;
        boolean nameEdit = false;
        boolean descriptEdit = false;
        View focusView = null;

        //check if fields were edited
        if(prevName.compareTo(newName) != 0){
            nameEdit = true;
        }
        if(prevDescription.compareTo(newDescript) !=0){
            descriptEdit = true;
        }

        //check if all fields were not edited
        if((nameEdit||descriptEdit) == false){
            cancel = true;
            if(!nameEdit){
                name.setError("You have not changed any fields. Please change a field or cancel editing");
                focusView = name;
            }
            else if(!descriptEdit){
                description.setError("You have not changed any fields. Please change a field or cancel editing");
                focusView = description;
            }
        }

        if(cancel){
            focusView.requestFocus();
        }
        else{
            DBHelper help = new DBHelper(User.user);
            help.editChore(new DBChoreView(chore.getId(), newName, newDescript, chore.getCurrentTurn(), chore.getAssignees()),
                    new AsyncResponseHandler<DBGenericResult>() {
                        @Override
                        public void onSuccess(DBGenericResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                            finish();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                            weFailed();
                        }

                        @Override
                        public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                            finish();
                        }
                    });
        }
    }

    private void weFailed(){
        Snackbar.make(findViewById(R.id.chores_list_edit_coordinator), "Edit Item Fail", Snackbar.LENGTH_SHORT);
    }

    private void sendRandomize() {
        DBHelper help = new DBHelper(User.user);
        help.editChore(new DBChoreView(chore.getId(), prevName, prevDescription,
                chore.getCurrentTurn(), chore.getAssignees()),
                new AsyncResponseHandler<DBGenericResult>() {
                    @Override
                    public void onSuccess(DBGenericResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        weFailed();
                    }

                    @Override
                    public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                        finish();
                    }
                });
    }

    private void cancel(){
        finish();
    }

    private void delete(){
        DBHelper help = new DBHelper(User.user);
        help.deleteChore(chore, new AsyncResponseHandler<DBGenericResult>() {
                    @Override
                    public void onSuccess(DBGenericResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        weFailed();
                    }

                    @Override
                    public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                        finish();
                    }
                });
    }
}
