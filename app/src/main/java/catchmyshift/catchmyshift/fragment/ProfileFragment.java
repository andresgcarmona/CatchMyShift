package catchmyshift.catchmyshift.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.activity.EditUserActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements OnMapReadyCallback{

    private MapView mMapView;
    private View v;
    LocationManager locationManager;
    private GoogleMap googleMap;
    private String avatar, fullname, email, about;
    String URL_DATA="http://67.205.138.130/";

    @BindString(R.string.title_Loading) String loadingText;
    @BindString(R.string.title_edit_profile)String editProfText;
    @BindString(R.string.title_no_data)String noDataText;
    @BindView(R.id.userAvatar) ImageView avatarUserIV;
    @BindView(R.id.idNameUser) TextView userFullname;
    @BindView(R.id.idEmailUser) TextView userEmail;
    @BindView(R.id.idAbout) TextView userAbout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,v);

        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
        LoadData();
        return v;

    }

    @OnClick(R.id.idEditUser)
    public void EditUser(Button btnEdit){
        btnEdit.setText(loadingText);
        Intent intent = new Intent().setClass(getContext(), EditUserActivity.class);
        startActivity(intent);
        btnEdit.setText(editProfText);
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(getActivity(),
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
                    locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
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

    public void LoadData(){
        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                avatar=bundle.getString("avatar");
                fullname=bundle.getString("fullname");
                email = bundle.getString("email");
                about = bundle.getString("about");


                String comparation = avatar.substring(0,1);
                Log.e("JMMC_ AVATAR RESULT", comparation);

                if(comparation.equals("h")){
                    Picasso.with(getContext()).load(avatar).fit().into(avatarUserIV);
                }
                else
                {
                    String FULL_URL_AVATAR = URL_DATA.concat(avatar);
                    Picasso.with(getContext()).load(FULL_URL_AVATAR).fit().into(avatarUserIV);
                }

                userFullname.setText(fullname);
                userEmail.setText(email);

                if(!about.equals("null"))
                {
                    userAbout.setText(about);
                }
                else
                {
                    userAbout.setText(noDataText);
                }
            }
        }
        catch (Exception e){
            Toast.makeText(getContext(),"error",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}




