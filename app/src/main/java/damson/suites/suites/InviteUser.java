package damson.suites.suites;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cz.msebera.android.httpclient.Header;

public class InviteUser extends AppCompatActivity {
    private EditText email_field;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_user);
        email_field = (EditText) findViewById(R.id.invite_suitemate);

        Button addButton = (Button)findViewById(R.id.send_invite);
        addButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                attemptInvite();
            }
        });



    }

    public void attemptInvite() {
        //reset errors
        email_field.setError(null);
        String email;

        //Store values at time of add attempt
        if(email_field.getText().toString().length() != 0) {
            email = email_field.getText().toString();
        }
        else{
            email = "";
        }


        boolean cancel = false;
        View focusView = null;

        //Check for valid item name
        //Assumes user puts in a name. Error only if empty
        if(TextUtils.isEmpty(email)) {
            email_field.setError("Please fill in the name of the item");
            focusView = email_field;
            cancel = true;
        }


        if(cancel){
            focusView.requestFocus();
        }
        else {
            DBInvitation item = new DBInvitation(email, Suite.suite.getId());

            // Upload item to grocery basket
            DBHelper helper = new DBHelper(User.user.getEmail(), User.user.getPassword());
            helper.makeInvitation(email, Suite.suite.getId(), new AsyncResponseHandler<DBGenericResult>() {
                @Override
                public void onSuccess(DBGenericResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.inviteLayout),
                            "Person you are trying to invite does not have an account", Snackbar.LENGTH_SHORT);
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
