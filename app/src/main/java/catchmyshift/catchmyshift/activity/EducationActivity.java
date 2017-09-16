package catchmyshift.catchmyshift.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    @BindView(R.id.sp_nivelestudio)
    Spinner nivelEstudio;
    @BindView(R.id.sp_nivelestudio_status) Spinner nivelEstudioStatus;

    private int mYear, mMonth, mDay;
    private String URL_DATAD = "http://67.205.138.130/api/degrees";
    private String URL_DATADS = "http://67.205.138.130/api/degrees-status";
    static final int READ_BLOCK_SIZE = 100;

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
            RequestDegreesStatus(FULL_TOKEN.toString());

        }
        catch (Exception e){
            Log.e("JMCC ERROR", e.getMessage());
        }
    }

    public void RequestDegrees(final String FULL_TOKEN){
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

                            ArrayAdapter spinner_adapter = new ArrayAdapter(EducationActivity.this, android.R.layout.simple_spinner_item, degrees);
                            nivelEstudio.setAdapter(spinner_adapter);

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

                            ArrayAdapter spinner_adapter = new ArrayAdapter(EducationActivity.this, android.R.layout.simple_spinner_item, degreesStatus);
                            nivelEstudioStatus.setAdapter(spinner_adapter);

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
}
