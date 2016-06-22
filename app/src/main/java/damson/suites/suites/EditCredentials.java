package damson.suites.suites;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class EditCredentials extends AppCompatActivity {
    private EditText new_name;
    private EditText new_email;
    private EditText new_pass_1;
    private EditText new_pass_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_credentials);
        /*Toolbar toolBar = (Toolbar) findViewById(R.id.intro_toolbar);
        setSupportActionBar(toolBar); */

        new_name = (EditText) findViewById(R.id.text_name_change);
        new_email = (EditText) findViewById(R.id.text_email_change);
        new_pass_1 = (EditText) findViewById(R.id.text1_password_change);
        new_pass_2 = (EditText) findViewById(R.id.text2_password_change);

        //Button listener
        Button newNameButton = (Button)findViewById(R.id.button_name_change);
        newNameButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                attemptNameChange();
            }
        });

        Button newEmailButton = (Button)findViewById(R.id.button_email_change);
        newEmailButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                attemptEmailChange();
            }
        });

        Button newPassButton = (Button)findViewById(R.id.button_password_change);
        newPassButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                attemptPasswordChange();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void attemptPasswordChange() {
        //reset errors
        new_pass_1.setError(null);
        new_pass_2.setError(null);

        String pass1;
        String pass2;

        //Store values at time of add attempt
        if(new_pass_1.getText().toString().length() != 0) {
            pass1 = new_pass_1.getText().toString();
        }
        else{
            pass1 = "";
        }
        if(new_pass_2.getText().toString().length() != 0) {
            pass2 = new_pass_2.getText().toString();
        }
        else{
            pass2 = "";
        }

        boolean cancel = false;
        View focusView = null;

        //Check for valid item name
        //Assumes user puts in a name. Error only if empty
        if(TextUtils.isEmpty(pass1)) {
            new_pass_1.setError("Please enter a new password");
            focusView = new_pass_1;
            cancel = true;
        }

        //Check for valid quantity
        //Assumes user provides quantity in the correct format. Error only if empty
        if(TextUtils.isEmpty(pass2) && !TextUtils.isEmpty(pass1)) {
            new_pass_2.setError("Please fill");
            focusView = new_pass_2;
            cancel = true;
        }

        if(pass1.length() < 6){
            new_pass_1.setError("Password must be at least 6 characters");
            focusView = new_pass_1;
            cancel=true;
        }

        else if(User.user.getPassword() == pass1){
            new_pass_1.setError("New password must be different");
            focusView = new_pass_1;
            cancel=true;
        }
        else if(!pass1.equals(pass2)){
            Log.d("hello", "Pass1 equals " + pass1 + "Pass2 equals" + pass2);
            new_pass_2.setError("Passwords must be the same");
            focusView = new_pass_2;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        //successful password change
        else{
            // Change passwords
            Log.d("hello", "Attempting password change");
            DBHelper helper = new DBHelper(User.user.getEmail(), User.user.getPassword());
            helper.editAccount(User.user.getEmail(), pass2, User.user.getName(), new AsyncResponseHandler<DBGenericResult>() {
                @Override
                public void onSuccess(DBGenericResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.edit_credentials), "Password successfully changed", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.edit_credentials),
                            "Couldn't find account", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    Log.e("EditCredentials", "Not in suite.");
                }

                @Override
                public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                    // TODO: Set up a "please login again" page
                }
            });
            //get the information from Leon on how to add to database
        }

    }

    //TODO: MAKE THIS A PUBLIC FUNCTION IN DBHELPER
    private boolean isEmailValid(String email) {
        //TODO: Email Validation
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(LoginActivity.EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private void attemptEmailChange() {
        //reset errors
        new_email.setError(null);

        String newEmail;

        //Store values at time of add attempt
        if(new_email.getText().toString().length() != 0) {
            newEmail = new_email.getText().toString();
        }
        else{
            newEmail = "";
        }

        boolean cancel = false;
        View focusView = null;

        if(!isEmailValid(newEmail)){
            new_email.setError("Email is not valid");
            focusView = new_email;
            cancel = true;

        }
        //Check for valid item name
        //Assumes user puts in a name. Error only if empty
        if(TextUtils.isEmpty(newEmail)) {
            new_email.setError("Please enter a new email");
            focusView = new_email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else {

            DBHelper helper = new DBHelper(User.user.getEmail(), User.user.getPassword());
            helper.editAccount(newEmail, User.user.getPassword(), User.user.getName(), new AsyncResponseHandler<DBGenericResult>() {
                @Override
                public void onSuccess(DBGenericResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.edit_credentials), "Email successfully changed", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.grocery_basket_add_coordinator),
                            "Couldn't find account", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    Log.e("GroceryBasketAdd", "Not in suite.");
                }

                @Override
                public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                    // TODO: Set up a "please login again" page
                }
            });
            //get the information from Leon on how to add to database
        }

    }

    private void attemptNameChange(){
        //reset errors
        new_name.setError(null);

        String newName;

        //Store values at time of add attempt
        if(new_name.getText().toString().length() != 0) {
            newName = new_name.getText().toString();
        }
        else{
            newName = "";
        }

        boolean cancel = false;
        View focusView = null;

        //Check for valid item name
        //Assumes user puts in a name. Error only if empty
        if(TextUtils.isEmpty(newName)) {
            new_name.setError("Please enter a new name");
            focusView = new_name;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else {

            DBHelper helper = new DBHelper(User.user.getEmail(), User.user.getPassword());
            helper.editAccount(User.user.getEmail(), User.user.getPassword(), newName, new AsyncResponseHandler<DBGenericResult>() {
                @Override
                public void onSuccess(DBGenericResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.edit_credentials), "Email successfully changed", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.edit_credentials),
                            "Couldn't find account", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    Log.e("GroceryBasketAdd", "Not in suite.");
                }

                @Override
                public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                    // TODO: Set up a "please login again" page
                }
            });
            //get the information from Leon on how to add to database
        }

    }
}
