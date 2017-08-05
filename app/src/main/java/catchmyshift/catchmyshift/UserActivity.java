package catchmyshift.catchmyshift;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager;
    private FragmentTransaction fragmentTransaction;
    private MyMethods myMethods = new MyMethods();
    private View currentView ;
    private Context context;
    private String data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();
        switch (id){
            case R.id.nav_profile:

                drawer.closeDrawer(GravityCompat.START);
                ProfileFragment profileFragment = new ProfileFragment();
                manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.layout_content_user,profileFragment,profileFragment.getTag()).commit();

                break;
            case R.id.nav_searchJob:
                Intent inst = new Intent().setClass(getApplicationContext(), MapsUserActivity.class);
                startActivity(inst);
                drawer.closeDrawer(GravityCompat.START);
                //mapUserFragment = new MapUserFragment();
                //manager = getSupportFragmentManager();
                //fragmentTransaction = manager.beginTransaction().replace(R.id.layout_content_user, mapUserFragment,mapUserFragment.getTag());
                //fragmentTransaction.commit();

                break;
            case R.id.nav_pay:
                PaymentFragment paymentFragment = new PaymentFragment();
                manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.layout_content_user,paymentFragment, paymentFragment.getTag()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_myevents:
               Intent myEventsIntent = new Intent().setClass(getApplicationContext(),MyEventsActivity.class);
                startActivity(myEventsIntent);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_settings:

                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_logoff:
                drawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent().setClass(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                break;

            default:

                drawer.closeDrawer(GravityCompat.START);
                break;
        }

        return true;
    }
}
