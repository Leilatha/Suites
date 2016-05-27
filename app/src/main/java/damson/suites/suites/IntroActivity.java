package damson.suites.suites;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewStub;

public class IntroActivity extends AppCompatActivity {

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.intro_toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.intro_fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSuiteMenu();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createSuiteMenu() {
        ViewStub stub = (ViewStub) findViewById(R.id.create_suite_stub);
        View inflated = stub.inflate();
        inflated.requestFocus();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSuite();
            }
        });
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
    }

    private void createSuite() {

    }

}
