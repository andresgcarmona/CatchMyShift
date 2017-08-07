package catchmyshift.catchmyshift;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by SILVER on 26/07/2017.
 */

public class CompanyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager manager;
    private FragmentTransaction fragmentTransaction;
    private MyMethods myMethods = new MyMethods();
    private View currentView ;
    private Context context;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_company);
        setSupportActionBar(toolbar);

        currentView = getWindow().getDecorView().findViewById(android.R.id.content);
        context = getApplicationContext();

        ProfileCompanyFragment profileFragment = new ProfileCompanyFragment();
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.layout_content_company,profileFragment,profileFragment.getTag()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_company);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_company);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_company);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_company);
        int id = item.getItemId();
        switch (id){
            case R.id.nav_profile_bussiness:
                    ProfileCompanyFragment profileFragment = new ProfileCompanyFragment();
                    manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.layout_content_company,profileFragment,profileFragment.getTag()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_contact:
                ContactCompanyFragment contactFragment = new ContactCompanyFragment();
                manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.layout_content_company,contactFragment,contactFragment.getTag()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_billing_info:
                BillingCompanyFragment billingFragment = new BillingCompanyFragment();
                manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.layout_content_company,billingFragment, billingFragment.getTag()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_publish_job:
                PublishJobCompanyFragment publishJob = new PublishJobCompanyFragment();
                manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.layout_content_company,publishJob, publishJob.getTag()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_myevents:
                EventsCompanyFragment myEvents = new EventsCompanyFragment();
                manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.layout_content_company, myEvents, myEvents.getTag()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_myopenevents:
                OpenEventsCompanyFragment openEvents = new OpenEventsCompanyFragment();
                manager= getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.layout_content_company, openEvents, openEvents.getTag()).commit();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_settings:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_logoff:
                drawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent().setClass(getApplicationContext(),LoginActivityCompany.class);
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
