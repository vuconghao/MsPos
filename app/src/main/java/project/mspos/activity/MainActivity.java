package project.mspos.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import project.mspos.R;
import project.mspos.fragments.FragmentDrawer;
import project.mspos.fragments.FragmentGiridViewProduct;
import project.mspos.utils.SessionManager;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{
    SessionManager session;
    FrameLayout layoutLeft,layoutRight;
    private FragmentManager fragmentManager;
    private FragmentGiridViewProduct fragmentGiridViewProduct;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        layoutLeft=(FrameLayout)findViewById(R.id.frame_left);
        layoutRight=(FrameLayout)findViewById(R.id.frame_right);
        fragmentManager=getFragmentManager();
        fragmentGiridViewProduct=new FragmentGiridViewProduct();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch (position) {
            case 0:
                Toast.makeText(this,"Account is cliked",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this,"Checkout is cliked",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this,"Order is cliked",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this,"On-holder order is cliked",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
