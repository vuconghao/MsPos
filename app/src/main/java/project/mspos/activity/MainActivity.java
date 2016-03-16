package project.mspos.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import project.mspos.R;
import project.mspos.utils.SessionManager;

public class MainActivity extends AppCompatActivity {
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session=new SessionManager(MainActivity.this);
        session.checkLogin();
    }
}
