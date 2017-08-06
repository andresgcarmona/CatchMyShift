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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_company);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_company);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        ProfileCompanyFragment profileCompanyFragment = new ProfileCompanyFragment();
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.layout_content_company,profileCompanyFragment,profileCompanyFragment.getTag()).commit();
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
                Intent inst = new Intent().setClass(getApplicationContext(), ProfileCompanyActivity.class);
                startActivity(inst);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_contact:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_billing_info:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_publish_job:

                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_myevents:

                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_myopenevents:
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
