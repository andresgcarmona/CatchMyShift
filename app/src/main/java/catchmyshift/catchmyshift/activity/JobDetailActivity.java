package catchmyshift.catchmyshift.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import catchmyshift.catchmyshift.R;

public class JobDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapView mMapView;
    GoogleMap googleMap;
    @BindView(R.id.idtitle_job) TextView jobName;
    @BindView(R.id.id_address) TextView jobAddress;
    @BindView(R.id.idvacantes) TextView jobVacancyNum;
    @BindView(R.id.idpublish_date)TextView jobPublishDate;
    @BindView(R.id.idfecha_inicio) TextView jobStartDate;
    @BindView(R.id.idfecha_final)TextView jobEndDate;
    @BindView(R.id.idhora_inicio)TextView jobStartTime;
    @BindView(R.id.idhora_final)TextView jobEndTime;
    @BindView(R.id.idsalario)TextView jobSalary;
    @BindView(R.id.idTask)TextView jobTasks;
    @BindView(R.id.idRequirements)TextView jobRequirements;
    @BindView(R.id.idCompany_Name) TextView companyName;
    @BindView(R.id.idJobCompany_Desc) TextView companyDescription;
    @BindView(R.id.idjob_ImageDetail) ImageView companyLogo;

    private Intent intent ;

    String URL_DATA="http://67.205.138.130/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        ButterKnife.bind(this);

        mMapView = (MapView) findViewById(R.id.idmap_job);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);

        LoadDataJobDetail();
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        intent = getIntent();
        float lat,lon;
        lat= Float.parseFloat(intent.getStringExtra("lat"));
        lon = Float.parseFloat(intent.getStringExtra("lon"));
        googleMap = mMap;
        LatLng pp = new LatLng(lat, lon);
        MarkerOptions option = new MarkerOptions();
        option.position(pp).title(intent.getStringExtra("job_name"));
        float zoomLevel = (float) 15.0;
        googleMap.addMarker(option);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pp, zoomLevel));

    }

    public void LoadDataJobDetail(){
        intent = getIntent();
        jobName.setText(intent.getStringExtra("job_name"));
        jobAddress.setText(intent.getStringExtra("address"));
        jobVacancyNum.setText(intent.getStringExtra("number_vacancies"));
        jobPublishDate.setText(intent.getStringExtra("publication_date"));
        jobStartDate.setText(intent.getStringExtra("start_date"));
        jobEndDate.setText(intent.getStringExtra("end_date"));
        jobStartTime.setText(intent.getStringExtra("start_time"));
        jobEndTime.setText(intent.getStringExtra("end_time"));
        jobSalary.setText("$" + intent.getStringExtra("salary"));
        jobTasks.setText(intent.getStringExtra("tasks"));
        jobRequirements.setText(intent.getStringExtra("requirements"));
        companyName.setText(intent.getStringExtra("name"));
        companyDescription.setText(intent.getStringExtra("description"));

        String avatar = intent.getStringExtra("logo");
        String comparation = avatar.substring(0,1);
        if(comparation.equals("h")){
            Picasso.with(JobDetailActivity.this).load(avatar).fit().into(companyLogo);
        }
        else
        {
            String FULL_URL_AVATAR = URL_DATA.concat(avatar);
            Picasso.with(JobDetailActivity.this).load(FULL_URL_AVATAR).fit().into(companyLogo);
        }

        //Picasso.with(getApplicationContext()).load(avatar).into(imageUser);
        //Log.e("JMMC_INTENTAVATAR",avatar);
    }
}
