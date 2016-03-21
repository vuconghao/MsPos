package project.mspos.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import project.mspos.R;
import project.mspos.fragments.FragmentGiridViewProduct;
import project.mspos.utils.SessionManager;

public class MainActivity extends AppCompatActivity {
    SessionManager session;
    FrameLayout layoutLeft,layoutRight;
    private FragmentManager fragmentManager;
    private FragmentGiridViewProduct fragmentGiridViewProduct;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session=new SessionManager(MainActivity.this);
        session.checkLogin();
        setupView();
        setInitialLayout();
        addListCategoryFragment();
    }

    private void addListCategoryFragment() {
        // Start a new FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // Add the TitleFragment to the layout
        fragmentTransaction.add(R.id.frame_left,
                fragmentGiridViewProduct);

        // Commit the FragmentTransaction
        fragmentTransaction.commit();
    }

    private void setInitialLayout() {
        layoutLeft.setLayoutParams(new LinearLayout.LayoutParams(0,
                MATCH_PARENT, 1f));
        layoutRight.setLayoutParams(new LinearLayout.LayoutParams(0,
                MATCH_PARENT, 1f));

    }

    private void setupView() {
        layoutLeft=(FrameLayout)findViewById(R.id.frame_left);
        layoutRight=(FrameLayout)findViewById(R.id.frame_right);
        fragmentManager=getFragmentManager();
        fragmentGiridViewProduct=new FragmentGiridViewProduct();
    }
}
