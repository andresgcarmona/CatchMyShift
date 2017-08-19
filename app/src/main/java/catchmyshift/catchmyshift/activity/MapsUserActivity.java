package catchmyshift.catchmyshift.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import catchmyshift.catchmyshift.utilities.MyMethods;
import catchmyshift.catchmyshift.R;

public class MapsUserActivity extends AppCompatActivity implements OnMapReadyCallback {

    private View currentView;
    private Context context;
    private String data;

    GoogleMap map;
    MyMethods myMethods = new MyMethods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentView = getWindow().getDecorView().findViewById(android.R.id.content);
        context = getApplicationContext();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (map.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                    map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                } else {
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng pp = new LatLng(16.860959782368866, -99.88278150558472);
        MarkerOptions option = new MarkerOptions();
        option.position(pp).title("Empresa de Desarrollo");
        float zoomLevel = (float) 16.0;
        map.addMarker(option);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pp, zoomLevel));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
            map.setMyLocationEnabled(true);

        data = getString(R.string.title_Success);
        myMethods.Success(currentView, data, context).show();
    }

}
