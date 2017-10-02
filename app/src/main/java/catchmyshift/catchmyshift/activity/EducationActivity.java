package catchmyshift.catchmyshift.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.adapter.SpinnerAdapter;

public class EducationActivity extends AppCompatActivity {

    @BindView(R.id.idanio_inicial)TextView anioInicial;
    @BindView(R.id.idanio_final)TextView anioFinal;
    @BindView(R.id.sp_nivelestudio) Spinner nivelEstudio;
    @BindView(R.id.sp_nivelestudio_status) Spinner nivelEstudioStatus;
    @BindView(R.id.id_institucion) EditText institucion;
    @BindView(R.id.idprogressEducation) SpinKitView progressBar;

    private int mYear, mMonth, mDay;
    private String URL_DATAD = "http://67.205.138.130/api/degrees";
    private String URL_DATADS = "http://67.205.138.130/api/degrees-status";
    static final int READ_BLOCK_SIZE = 100;
    public static String action = "";

    private Intent intent;

    ArrayAdapter degree_adapter;
    ArrayAdapter degree_status_adapter;

    public class LoadExtras
    {
        public int PDegreeName;
        public int PDegreeStatus;
        public String Institution;
        public String StartYear;
        public String EndYear;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);
        ButterKnife.bind(this);
        LoadToken();
    }

    @OnClick(R.id.idanio_inicial)
    public void SetStartDate(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        anioInicial.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.idanio_final)
    public void SetEndDate(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        anioFinal.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void LoadToken(){
        try {
            FileInputStream fileIn = this.openFileInput("cmsoa.sm");
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
            RequestDegrees(FULL_TOKEN.toString());
            //RequestDegreesStatus(FULL_TOKEN.toString());
        }
        catch (Exception e){
            Log.e("JMCC ERROR", e.getMessage());
        }
    }

    public void RequestDegrees(final String FULL_TOKEN){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATAD,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {

                            JSONArray arrayWE = new JSONArray(response);

                            ArrayList<SpinnerAdapter> degreesArrayList = new ArrayList<SpinnerAdapter>();
                            ArrayList<String> degrees = new ArrayList<String>();


                            for(int i = 0; i < arrayWE.length(); i++)
                            {
                                JSONObject objectWE = arrayWE.getJSONObject(i);

                                SpinnerAdapter degreesItem = new SpinnerAdapter();
                                degreesItem.setId(objectWE.getInt("id"));
                                degreesItem.setName(objectWE.getString("name"));
                                degreesArrayList.add(degreesItem);

                                degrees.add(objectWE.getString("name"));
                            }

                            degree_adapter = new ArrayAdapter(EducationActivity.this, android.R.layout.simple_spinner_item, degrees);
                            nivelEstudio.setAdapter(degree_adapter);
                            RequestDegreesStatus(FULL_TOKEN.toString());

                        } catch (JSONException e) {
                            Log.e("JMMC_USER",e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("JMMC","Error " + error.getMessage());
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", FULL_TOKEN);
                Log.e("JMMC", "HEADERS_USERACTIVITY");
                return headers;
            }};

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void RequestDegreesStatus(final String FULL_TOKEN){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATADS,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {

                            JSONArray arrayWE = new JSONArray(response);

                            ArrayList<SpinnerAdapter> degreesStatusArrayList = new ArrayList<SpinnerAdapter>();
                            ArrayList<String> degreesStatus = new ArrayList<String>();


                            for(int i = 0; i < arrayWE.length(); i++)
                            {
                                JSONObject objectWE = arrayWE.getJSONObject(i);

                                SpinnerAdapter degreesStatusItem = new SpinnerAdapter();
                                degreesStatusItem.setId(objectWE.getInt("id"));
                                degreesStatusItem.setName(objectWE.getString("name"));
                                degreesStatusArrayList.add(degreesStatusItem);

                                degreesStatus.add(objectWE.getString("name"));
                            }

                            degree_status_adapter = new ArrayAdapter(EducationActivity.this, android.R.layout.simple_spinner_item, degreesStatus);
                            nivelEstudioStatus.setAdapter(degree_status_adapter);

                            new RequestActionAT().execute();

                        } catch (JSONException e) {
                            Log.e("JMMC_USER",e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("JMMC","Error " + error.getMessage());
                    }

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", FULL_TOKEN);
                Log.e("JMMC", "HEADERS_USERACTIVITY");
                return headers;
            }};

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public class RequestActionAT extends AsyncTask<Void,Void,LoadExtras>{

        @Override
        protected LoadExtras doInBackground(Void... params) {

            LoadExtras loadExtras = new LoadExtras();
            try{
                intent = getIntent();
                action = intent.getStringExtra("action");
                if(action.equals("edit")){

                    String degreeName = intent.getStringExtra("academic_degree_name");
                    loadExtras.PDegreeName = degree_adapter.getPosition(degreeName);

                    String degreeStatusName = intent.getStringExtra("academic_degree_status_name");
                    loadExtras.PDegreeStatus = degree_status_adapter.getPosition(degreeStatusName);

                    String instution = intent.getStringExtra("academic_institution");
                    String startYear = intent.getStringExtra("academic_start_year");
                    String endYear = intent.getStringExtra("academic_end_year");

                    loadExtras.Institution = instution;
                    loadExtras.StartYear = startYear;
                    loadExtras.EndYear = endYear;

                }
                return loadExtras;
            }
            catch (Exception e){
                Log.e("JMMC_ERROR_IN",e.getMessage());
                return loadExtras;
            }

        }

        @Override
        protected void onPostExecute(LoadExtras loadExtras) {
            if(action.equals("edit")) {
                nivelEstudio.setSelection(loadExtras.PDegreeName);
                nivelEstudioStatus.setSelection(loadExtras.PDegreeStatus);

                institucion.setText(loadExtras.Institution);
                anioInicial.setText(loadExtras.StartYear);
                anioFinal.setText(loadExtras.EndYear);
            }

            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    /*    public void RequestAction(){
        intent = getIntent();
        action = intent.getStringExtra("action");

        if(action.equals("edit")){
            String degreeName = intent.getStringExtra("academic_degree_name");
            int pos = degree_adapter.getPosition(degreeName);
            nivelEstudio.setSelection(pos);

            String degreeStatusName = intent.getStringExtra("academic_degree_status_name");
            pos = degree_status_adapter.getPosition(degreeStatusName);
            nivelEstudioStatus.setSelection(pos);

            institucion.setText(intent.getStringExtra("academic_institution"));
            anioInicial.setText(intent.getStringExtra("academic_start_year"));
            anioFinal.setText(intent.getStringExtra("academic_end_year"));

        }
    } */
}
