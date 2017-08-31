package catchmyshift.catchmyshift.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
public class ProfileFragment extends Fragment {

    private MapView mMapView;
    private View v;
    LocationManager locationManager;
    private GoogleMap googleMap;
    private String avatar, fullname, email, about, job_position, company, start_date, end_date;
    String URL_DATA="http://67.205.138.130/";

    @BindString(R.string.title_Loading) String loadingText;
    @BindString(R.string.title_edit_profile)String editProfText;
    @BindString(R.string.title_no_data)String noDataText;
    @BindView(R.id.userAvatar) ImageView avatarUserIV;
    @BindView(R.id.idNameUser) TextView userFullname;
    @BindView(R.id.idEmailUser) TextView userEmail;
    @BindView(R.id.idAbout) TextView userAbout;
    @BindView(R.id.idWorkingxp) TextView workingExp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,v);
        LoadData();
        return v;

    }

    @OnClick(R.id.idEditUser)
    public void EditUser(Button btnEdit){
        Intent intent = new Intent().setClass(getContext(), EditUserActivity.class);
        String comparation = avatar.substring(0,1);
        if(comparation.equals("h")){
            intent.putExtra("avatar",avatar);
        }
        else
        {
            String FULL_URL_AVATAR = URL_DATA.concat(avatar);
            intent.putExtra("avatar",FULL_URL_AVATAR);
        }



        Log.e("JMMC_INTENTAVATAR",intent.toString());
        startActivity(intent);
    }

    public void LoadData(){
        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                avatar=bundle.getString("avatar");
                fullname=bundle.getString("fullname");
                email = bundle.getString("email");
                about = bundle.getString("about");
                job_position = bundle.getString("job_position");
                company = bundle.getString("company");
                start_date = bundle.getString("start_date");
                end_date = bundle.getString("end_date");

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

                if(!job_position.equals("null")){
                    workingExp.setText(job_position + "\n" + company + "\n" + start_date + " - " + end_date);
                }
                else{
                    workingExp.setText(noDataText);
                }
            }
        }
        catch (Exception e){
            Toast.makeText(getContext(),"error",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


}




