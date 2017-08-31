package catchmyshift.catchmyshift.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import catchmyshift.catchmyshift.R;

public class JobDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapView mMapView;
    GoogleMap googleMap;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        mMapView = (MapView) findViewById(R.id.idmap_job);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            return;
        }
        else
        {
            try
            {
                googleMap.setMyLocationEnabled(true);
                locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
                Criteria cri = new Criteria();

                Location loc = locationManager.getLastKnownLocation(locationManager.getBestProvider(cri,false));
                CameraPosition camPos = new CameraPosition.Builder().target(new LatLng(loc.getLatitude(),loc.getLongitude())).zoom(15.1f).build();
                CameraUpdate camUpdate = CameraUpdateFactory.newCameraPosition(camPos);
                googleMap.moveCamera(camUpdate);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
