package damson.suites.suites;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_CONTACTS;

public class RegistrationActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private UserRegistrationTask mRegistrationForm = null;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mNameView;
    private View mProgressView;
    private View mRegistrationFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email_registration);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password_registration);

        mNameView = (EditText) findViewById(R.id.name_registration);

        mNameView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.registration || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_button_2);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });

        mEmailView.setText(getIntent().getStringExtra("email"));
        mPasswordView.setText(getIntent().getStringExtra("password"));


        mRegistrationFormView = findViewById(R.id.registration_form);
        mProgressView = findViewById(R.id.registration_progress);
    }

    private void attemptRegistration() {
        //TODO: Registration
        if (mRegistrationForm != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the registration attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String name = mNameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password
        if(TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_registration_failed_no_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (!TextUtils.isEmpty(password) && !isPasswordShort(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (!TextUtils.isEmpty(password) && !isPasswordLong(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password_long));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_registration_failed_no_email));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a name.
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_registration_failed_no_name));
            focusView = mNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mRegistrationForm = new UserRegistrationTask(email, password, name);
            mRegistrationForm.execute((Void[]) null);
            /*if(!mRegistrationForm.isCancelled()) {
                gotoGroceryBasket(null);
            }*/
        }
    }


    private boolean isPasswordShort(String password) {
        //TODO: Password Verification
        if (password.length() < 6){
            return false;
        }

        return true;
    }

    private boolean isPasswordLong(String password) {
        if(password.length() > 100){
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        //TODO: Email Validation
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile(LoginActivity.EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegistrationFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there will not be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }
    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }
        addEmailsToAutoComplete(emails);
    }
    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegistrationActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    public class UserRegistrationTask extends AsyncTask<Void, Void, Boolean> {
        private final String mEmail;
        private final String mName;
        private final String mPassword;
        private boolean notAlreadyRegistered = false;


        UserRegistrationTask(String email, String password, String name) {
            mEmail = email;
            mName = name;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            URL url = null;
            HttpURLConnection client = null;

            RegistrationForm user = new RegistrationForm(mEmail, mName, mPassword);

            try {
                url = new URL("http://104.236.61.10:8080/account");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                client = (HttpURLConnection) url.openConnection();
            } catch (SocketTimeoutException error){
                // Handles URL access timeout
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Register an account
            try {
                // Setup account
                client.setReadTimeout(7000);
                client.setConnectTimeout(7000);
                client.setChunkedStreamingMode(0);
                client.setRequestMethod("POST");
                client.setRequestProperty("Content-Type","application/json");
                client.setDoOutput(true);

                // Output stream to server
                OutputStream outputPOST = client.getOutputStream();//new BufferedOutputStream(client.getOutputStream());
                DBHelper.mapper.writeValue(outputPOST, user);
                outputPOST.flush();
                outputPOST.close();

                int responseCode = client.getResponseCode();
                JsonNode tree = DBHelper.mapper.readValue(client.getInputStream(), JsonNode.class);
                notAlreadyRegistered = tree.findValue("success").asBoolean();
                client.disconnect();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    if(notAlreadyRegistered) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }

                // Set POST length for performance reasons
                client.setChunkedStreamingMode(0);
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (JsonGenerationException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mRegistrationForm = null;
            showProgress(false);

            if (success) {
                //IF THE Registration WORKED, GO TO LoginActivity, using Activity Stack
                Intent intent = new Intent();
                intent.putExtra("email", mEmail);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                mRegistrationForm = null;
                showProgress(false);
                if(notAlreadyRegistered) {
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.registration_coordinator),
                            R.string.error_registration_failed, Snackbar.LENGTH_SHORT);
                    mySnackbar.show();

                }
                else {
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.registration_coordinator),
                            R.string.error_registration_failed_invalid_email, Snackbar.LENGTH_SHORT);
                    mySnackbar.show();

                }

            }
        }

        @Override
        protected void onCancelled() {
            mRegistrationForm = null;
            showProgress(false);
        }
    }
}
