package damson.suites.suites;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class IntroActivity extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout;
    FloatingActionButton fab;
    View inflated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.three_buttons_activity);

        fab = (FloatingActionButton) findViewById(R.id.intro_fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSuiteMenu();
            }
        });
        createInviteList();
    }

    private void createInviteList() {
        DBHelper helper = new DBHelper(User.user);
        helper.getUserInvites(new AsyncResponseHandler<Suite[]>() {
            @Override
            public void onSuccess(Suite[] response, int statusCode, Header[] headers, byte[] errorResponse) {
                if(response.length == 0) {  //Does not have invites
                    setNoInviteText(true);
                }
                else {  // Has invites
                    setNoInviteText(false);
                    InviteAdapter inviteAdapter = new InviteAdapter(getApplicationContext(), response);
                    ListView inviteList = (ListView) findViewById(R.id.intro_suite_invite_list);
                    inviteList.setAdapter(inviteAdapter);
                    final ListView myList = (ListView) findViewById(R.id.intro_suite_invite_list);
                    myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            joinSuite((Suite)myList.getAdapter().getItem(position));
                        }
                    });
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

            }

            @Override
            public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {

            }

            @Override
            public void onFinish() {
            }
        });
    }

    private void joinSuite(final Suite item) {
        DBHelper helper = new DBHelper(User.user);
        helper.joinSuite(item.getId(), new AsyncResponseHandler<DBGenericResult>() {
            @Override
            public void onSuccess(DBGenericResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                Toast.makeText(IntroActivity.this, "Joined!", Toast.LENGTH_SHORT).show();
                Suite.suite = item;
                Intent intent = new Intent(IntroActivity.this, ThreeButtonsActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Toast.makeText(IntroActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {

            }
        });
    }

    private void setNoInviteText(boolean setText) {
        ListView myList = (ListView) findViewById(R.id.intro_suite_invite_list);
        TextView tv = (TextView) findViewById(R.id.intro_no_invites_view);;

        // If no Items...
        if (setText) {
            if (myList != null)
                myList.setVisibility(View.GONE);

            tv.setVisibility(View.VISIBLE);
            System.out.println("NOTE: no items in myList");
            return;
        }

        // There are items
        if (myList != null) {
            myList.setVisibility(View.VISIBLE);
            tv.setVisibility(View.GONE);
            return;
        }
        else {
            System.out.println("ERROR: myList not initialized");
            return;
        }
    }

    private void createSuiteMenu() {
        ViewStub stub = null;
        EditText field = null;
        if(inflated == null) {
            stub = (ViewStub) findViewById(R.id.create_suite_stub);

            inflated = stub.inflate();

            field = (EditText) findViewById(R.id.create_suite_name_view);
            inflated.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
            field.requestFocus();

            Button button = (Button) findViewById(R.id.create_suite_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createSuite();
                }
            });

            field.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.create_suite_confirm || id == EditorInfo.IME_NULL) {
                        createSuite();
                        return true;
                    }
                    return false;
                }
            });
        }
        inflated.requestFocus();
    }

    private void createSuite() {
        DBHelper helper = new DBHelper(User.user);
        EditText field = (EditText) findViewById(R.id.create_suite_name_view);

        helper.addSuite(field.getText().toString(), new AsyncResponseHandler<DBGenericResult>() {
            @Override
            public void onSuccess(DBGenericResult response, int statusCode, Header[] headers, byte[] errorResponse) {
                if(response.getSuccess()) {
                    getSuite(User.user);
                }
                else {
                    Snackbar.make(findViewById(R.id.create_suite_coordinator),
                            R.string.create_suite_error_basic, Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Snackbar.make(findViewById(R.id.create_suite_coordinator),
                        R.string.error_network_connection, Snackbar.LENGTH_SHORT);
            }

            @Override
            public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                // SHOULD NEVER HAPPEN
            }
        });
    }

    private void getSuite(User user) {
        DBHelper helper = new DBHelper(user);
        helper.getUserSuites(new AsyncResponseHandler<List<Suite>>() {
            @Override
            public void onSuccess(List<Suite> response, int statusCode, Header[] headers, byte[] errorResponse) {
                //GO TO INTROACTIVITY
                Suite.suite = response.get(0);
                Intent intent = new Intent(IntroActivity.this, ThreeButtonsActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                //SHOULD NEVER HAPPEN
            }

            @Override
            public void onLoginFailure(Header[] headers, byte[] errorResponse, Throwable e) {
                //ALSO SHOULD NEVER HAPPEN
            }
        });
    }

    private void showProgress(final boolean show) {
        View coordinator = findViewById(R.id.create_suite_coordinator);
        View progress = findViewById(R.id.create_suite_progress);
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
        coordinator.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.logout:
                User.user = null;
                Suite.suite = null;
                Intent j = new Intent(this, LoginActivity.class);
                finish();
                startActivity(j);
                break;


        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
    }
}
