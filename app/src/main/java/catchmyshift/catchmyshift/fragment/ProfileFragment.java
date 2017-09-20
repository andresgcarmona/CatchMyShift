package catchmyshift.catchmyshift.fragment;


import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.activity.EditUserActivity;
import catchmyshift.catchmyshift.adapter.AcademicLevelAdapter;
import catchmyshift.catchmyshift.adapter.LanguagesAdapter;
import catchmyshift.catchmyshift.adapter.WorkExperienceAdapter;
import catchmyshift.catchmyshift.listitem.AcademicLevelListItem;
import catchmyshift.catchmyshift.listitem.LanguagesListItem;
import catchmyshift.catchmyshift.listitem.WorkExperienceListItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private MapView mMapView;
    private View v;
    LocationManager locationManager;
    private GoogleMap googleMap;
    private String avatar, fullname, name, email, about, lastName, birthdate, job_position, company, start_date, end_date;
    String URL_DATA="http://67.205.138.130/";
    static final int READ_BLOCK_SIZE = 100;


    @BindString(R.string.title_Loading) String loadingText;
    @BindString(R.string.title_edit_profile)String editProfText;
    @BindString(R.string.title_no_data)String noDataText;
    @BindView(R.id.userAvatar) ImageView avatarUserIV;
    @BindView(R.id.idNameUser) TextView userFullname;
    @BindView(R.id.idEmailUser) TextView userEmail;
    @BindView(R.id.idAbout) TextView userAbout;

    private RecyclerView.Adapter adapter;

    private List<WorkExperienceListItem> workExperienceListItems;
    private List<AcademicLevelListItem> academicLevelListItems;
    private List<LanguagesListItem> languagesListItems;

    private String URL_DATAWE = "http://67.205.138.130/api/work-experience";
    private String URL_DATAAL = "http://67.205.138.130/api/education";
    private String URL_DATAL = "http://67.205.138.130/api/ulanguage";

    RecyclerView workExpRecyclerView;
    RecyclerView academicLevelsRecyclerView;
    RecyclerView languageRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        workExpRecyclerView = (RecyclerView) v.findViewById(R.id.idworkexperience_recyclerView);
        workExpRecyclerView.setHasFixedSize(true);
        workExpRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        workExperienceListItems = new ArrayList<>();

        academicLevelsRecyclerView = (RecyclerView) v.findViewById(R.id.idacademiclevels_recyclerView);
        academicLevelsRecyclerView.setHasFixedSize(true);
        academicLevelsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        academicLevelListItems = new ArrayList<>();

        languageRecyclerView = (RecyclerView) v.findViewById(R.id.idlanguages_recyclerView);
        languageRecyclerView.setHasFixedSize(true);
        languageRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        languagesListItems = new ArrayList<>();

        ButterKnife.bind(this, v);
        LoadData();
        LoadToken();

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

        intent.putExtra("name",name);
        intent.putExtra("email",email);
        intent.putExtra("about",about);
        intent.putExtra("lastname",lastName);
        intent.putExtra("birthdate",birthdate);

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
                name = bundle.getString("name");
                lastName = bundle.getString("lastname");
                birthdate = bundle.getString("birthdate");


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

    public void LoadToken(){
        try {
            FileInputStream fileIn = getActivity().openFileInput("cms.sm");
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String FULL_TOKEN = "";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                FULL_TOKEN += readstring;
            }
            InputRead.close();
            RequestWorkExperience(FULL_TOKEN.toString());
            RequestAcademicLevel(FULL_TOKEN.toString());
            RequestLanguage(FULL_TOKEN.toString());

        }
        catch (Exception e){
            Log.e("JMCC ERROR", e.getMessage());
        }
    }

    public void RequestWorkExperience(final String FULL_TOKEN){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATAWE,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {

                            JSONArray arrayWE = new JSONArray(response);

                            for(int i = 0; i < arrayWE.length(); i++)
                            {
                                JSONObject objectWE = arrayWE.getJSONObject(i);

                                WorkExperienceListItem workExperienceListItem = new WorkExperienceListItem(
                                        objectWE.getString("job_position"),
                                        objectWE.getString("company"),
                                        objectWE.getString("start_date") + " - " + objectWE.getString("end_date"));

                                workExperienceListItems.add(workExperienceListItem);
                            }

                            adapter = new WorkExperienceAdapter(workExperienceListItems, getContext());
                            workExpRecyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Log.e("JMMC_USER",e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", FULL_TOKEN);
                Log.e("JMMC", "HEADERS_USERACTIVITY");
                return headers;
            }};

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void RequestAcademicLevel(final String FULL_TOKEN){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATAAL,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {

                            JSONArray arrayAL = new JSONArray(response);

                            for(int i = 0; i < arrayAL.length(); i++)
                            {
                                JSONObject objectAL = arrayAL.getJSONObject(i);
                                JSONObject objectD = objectAL.getJSONObject("degree");
                                JSONObject objectS = objectAL.getJSONObject("status");

                                AcademicLevelListItem academicLevelListItem = new AcademicLevelListItem(
                                        objectAL.getString("id"), objectAL.getString("user_id"), objectAL.getString("degree_id"),
                                        objectAL.getString("degree_status_id"), objectD.getString("name"), objectS.getString("name"),
                                        objectD.getString("name") + "-" + objectS.getString("name"), objectAL.getString("institute"),
                                        objectAL.getString("start_year"), objectAL.getString("end_year"),
                                        objectAL.getString("start_year") + " - " + objectAL.getString("end_year"),
                                        objectAL.getString("certificate"), objectAL.getString("certificate_name"),
                                        objectAL.getString("created_at"), objectAL.getString("updated_at"), objectAL.getString("deleted_at"));

                                academicLevelListItems.add(academicLevelListItem);
                            }

                            adapter = new AcademicLevelAdapter(academicLevelListItems, getContext());
                            academicLevelsRecyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Log.e("JMMC_USER",e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", FULL_TOKEN);
                Log.e("JMMC", "HEADERS_USERACTIVITY");
                return headers;
            }};

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void RequestLanguage(final String FULL_TOKEN){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATAL,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {

                            JSONArray arrayL = new JSONArray(response);

                            for(int i = 0; i < arrayL.length(); i++)
                            {
                                JSONObject objectL = arrayL.getJSONObject(i);

                                LanguagesListItem languagesListItem = new LanguagesListItem(
                                        objectL.getString("language_id"),
                                        objectL.getString("level"),
                                        objectL.getString("comments"));

                                languagesListItems.add(languagesListItem);
                            }

                            adapter = new LanguagesAdapter(languagesListItems, getContext());
                            languageRecyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Log.e("JMMC_USER",e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", FULL_TOKEN);
                Log.e("JMMC", "HEADERS_USERACTIVITY");
                return headers;
            }};

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}




