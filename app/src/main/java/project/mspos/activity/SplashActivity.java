package project.mspos.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import project.mspos.R;
import project.mspos.fragments.FragmentLogin;
import project.mspos.fragments.FragmentSplashScreen;
import project.mspos.utils.AlertDialogManager;
import project.mspos.utils.SessionManager;

/**
 * Created by SON on 3/16/2016.
 */
public class SplashActivity extends AppCompatActivity implements FragmentLogin.CheckLogin {
    FragmentManager fragmentManager;
    AlertDialogManager alertDialogManager;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fragmentManager=getFragmentManager();
        alertDialogManager=new AlertDialogManager();
        sessionManager=new SessionManager(getApplicationContext());
        addSplashFragment();
        new SplashAsyncTask().execute();
    }


    private void addLoginFragment() {
        FragmentLogin fragmentLogin=new FragmentLogin();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.frame_splash_and_login, fragmentLogin);
        fragmentTransaction.commit();
    }

    private void addSplashFragment() {
        FragmentSplashScreen fragmentSplashScreen=new FragmentSplashScreen();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.frame_splash_and_login, fragmentSplashScreen);
        fragmentTransaction.commit();

    }

    @Override
    public void setLoggedIn(boolean isLoggedIn) {
        if(isLoggedIn){
            sessionManager.createLoginSession(FragmentLogin.DEFAULT_PASS,FragmentLogin.DEFAULT_EMAIL);

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        }else {
            alertDialogManager.showAlertDialog(SplashActivity.this, "Login failed..", "Username/Password is incorrect", false);
        }

    }

    public class SplashAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            SystemClock.sleep(2000);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            addLoginFragment();
        }
    }
}
