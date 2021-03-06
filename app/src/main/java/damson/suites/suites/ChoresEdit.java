package damson.suites.suites;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.util.CircularArray;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Button doneButton = (Button)findViewById(R.id.chores_list_edit_mark_done);
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
                randomizeAndSave();
            }
        });
        doneButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                markDone();
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

                        @Override
                        public void onRetry() {
                            retry();
                        }
                    });
        }
    }

    private void weFailed(){
        Snackbar.make(findViewById(R.id.chores_list_edit_coordinator), "Edit Item Fail", Snackbar.LENGTH_SHORT).show();
    }

    private void sendRandomize(DBChoreView choreView) {

        DBHelper help = new DBHelper(User.user);
        help.editChore(choreView,
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

                    @Override
                    public void onRetry() {
                        retry();
                    }
                });
    }

    private void retry() {
        Snackbar.make(findViewById(R.id.chores_list_edit_coordinator),
                "Could not connect. Retrying...", Snackbar.LENGTH_SHORT).show();
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

    public void randomizeAndSave(){
        List<User> assignees = chore.getAssignees();
        User [] users = new User[assignees.size()];
        for(int i = 0; i < assignees.size(); i++){
            users[i] = assignees.get(i);
        }

        //set up random to return random indices
        Random random = new Random();
        int index = random.nextInt(users.length);
        //stores indices already stored
        ArrayList<Integer> found = new ArrayList<>(users.length);

        CircularArray<User> rotation = new CircularArray<>(users.length);

        //Loop to fill in rotation  //This could accidentally run for a long time
        while(rotation.size() < users.length){
            if(!found.contains(index)) {
                rotation.addLast(users[index]);
                found.add(new Integer(index));
                index = random.nextInt(users.length);
            }
            else{
                index = random.nextInt(users.length);
            }
        }

        //put new rotation into assignees
        for(int i = 0; i < rotation.size(); i++){
            assignees.remove(i);
            assignees.add(i, rotation.get(i));
        }

        //store values at time of edit attempt
        String newName = name.getText().toString();
        String newDescript = description.getText().toString();

        sendRandomize(new DBChoreView(chore.getId(), newName, newDescript, 0, assignees));
    }

    private void markDone(){
        DBHelper helper = new DBHelper(User.user);
        helper.advanceChore(chore, new AsyncResponseHandler<DBGenericResult>() {
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
                weFailed();
            }
        });
    }

    private List<User> updateAssignees() {
        DBHelper helper = new DBHelper(User.user);
        helper.listUsersInASuite(Suite.suite.getId(), new AsyncResponseHandler<DBUserListResult>() {
            @Override
            public void onSuccess(DBUserListResult response, int statusCode, Header[] headers, byte[] errorResponse) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

            }

            @Override
            public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {

            }
        });
        return null;
    }
}
