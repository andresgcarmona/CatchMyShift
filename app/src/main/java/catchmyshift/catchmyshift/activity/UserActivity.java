package catchmyshift.catchmyshift.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.List;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.utilities.MyMethods;
import catchmyshift.catchmyshift.fragment.PaymentFragment;
import catchmyshift.catchmyshift.fragment.ProfileFragment;
import catchmyshift.catchmyshift.R;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager;
    private FragmentTransaction fragmentTransaction;
    private MyMethods myMethods = new MyMethods();
    private View currentView ;
    private Context context;
    private String data;

    private LinearLayout mRevealView;
    private boolean hidden = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mRevealView = (LinearLayout) findViewById(R.id.reveal_items);
        mRevealView.setVisibility(View.INVISIBLE);

        currentView = getWindow().getDecorView().findViewById(android.R.id.content);
        context = getApplicationContext();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        ProfileFragment profileFragment = new ProfileFragment();
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.layout_content_user,profileFragment,profileFragment.getTag()).commit();
    }

    private void hideRevealView() {
        if (mRevealView.getVisibility() == View.VISIBLE) {
            int cx = (mRevealView.getLeft() + mRevealView.getRight());
            int cy = mRevealView.getTop();
            int radius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());
            Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mRevealView.setVisibility(View.INVISIBLE);
                    hidden = true;
                }
            });
            anim.start();
            hidden = true;
        }
    }

    @OnClick({R.id.nav_profile,R.id.nav_searchJob,R.id.nav_pay,R.id.nav_myevents,R.id.nav_settings,R.id.nav_logoff})
    public void ActionReveal(View view){
        hideRevealView();
        switch (view.getId()){
            case R.id.nav_profile:
                ProfileFragment profileFragment = new ProfileFragment();
                manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.layout_content_user,profileFragment,profileFragment.getTag()).commit();
                break;

            case R.id.nav_searchJob:
                Intent inst = new Intent().setClass(getApplicationContext(), MapsUserActivity.class);
                startActivity(inst);
                break;

            case R.id.nav_pay:
                PaymentFragment paymentFragment = new PaymentFragment();
                manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.layout_content_user,paymentFragment, paymentFragment.getTag()).commit();
                break;

            case R.id.nav_myevents:
                Intent myEventsIntent = new Intent().setClass(getApplicationContext(),MyEventsActivity.class);
                startActivity(myEventsIntent);
                break;

            case R.id.nav_settings:

                break;

            case R.id.nav_logoff:
                Intent intent = new Intent().setClass(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_submenu:

                int cx = (mRevealView.getLeft() + mRevealView.getRight());
                int cy = mRevealView.getTop();
                int radius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());

                if (hidden) {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
                mRevealView.setVisibility(View.VISIBLE);
                anim.start();
                hidden = false;
            } else {
                Animator anim = android.view.ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        mRevealView.setVisibility(View.INVISIBLE);
                        hidden = true;
                    }
                });
                anim.start();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();
        switch (id){
            case R.id.option1:
                drawer.closeDrawer(GravityCompat.START);

                break;
            case R.id.option2:

                drawer.closeDrawer(GravityCompat.START);
                //mapUserFragment = new MapUserFragment();
                //manager = getSupportFragmentManager();
                //fragmentTransaction = manager.beginTransaction().replace(R.id.layout_content_user, mapUserFragment,mapUserFragment.getTag());
                //fragmentTransaction.commit();

                break;
            case R.id.option3:

                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.option4:

                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.option5:

                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.option6:
                drawer.closeDrawer(GravityCompat.START);

                break;

            default:

                drawer.closeDrawer(GravityCompat.START);
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
