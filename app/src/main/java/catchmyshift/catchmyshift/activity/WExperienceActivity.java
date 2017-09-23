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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.adapter.SpinnerAdapter;


public class WExperienceActivity extends AppCompatActivity {

    @BindView(R.id.idfecha_inicial)TextView fechaInicial;
    @BindView(R.id.idfecha_final)TextView fechaFinal;
    @BindView(R.id.sp_sector_industrial)
    SearchableSpinner sectorIndustrialSP;
    @BindView(R.id.idprogressLoadWExp) SpinKitView progressBar;
    @BindString(R.string.title_describe_industria) String titleIndustrial;

    private int mYear, mMonth, mDay;
    private String URL_DATAIS = "http://67.205.138.130/api/industrial-sectors";
    static final int READ_BLOCK_SIZE = 100;
    private Intent intent;
    public static String action = "";


    public class LoadExtras
    {
        public String Job;
        public int PosIndSector;
        public String Company;
        public String Description;
        public String StartDate;
        public String EndDate;
        public String SupervisorName;
        public String SupervisorPhone;
        public boolean ActualJob;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wexperience);
        ButterKnife.bind(this);
        sectorIndustrialSP.setTitle(titleIndustrial);
        LoadToken();
    }

    @OnClick(R.id.idfecha_inicial)
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

                        fechaInicial.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.idfecha_final)
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

                        fechaFinal.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

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
            RequestIndustrialSectors(FULL_TOKEN.toString());

        }
        catch (Exception e){
            Log.e("JMCC ERROR", e.getMessage());
        }
    }

    public void RequestIndustrialSectors(final String FULL_TOKEN){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATAIS,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {

                            JSONArray arrayWE = new JSONArray(response);

                            ArrayList<SpinnerAdapter> industrialSectorsArrayList = new ArrayList<SpinnerAdapter>();
                            ArrayList<String> industrialSectors = new ArrayList<String>();


                            for(int i = 0; i < arrayWE.length(); i++)
                            {
                                JSONObject objectWE = arrayWE.getJSONObject(i);

                                SpinnerAdapter industrialSectorsItem = new SpinnerAdapter();
                                industrialSectorsItem.setId(objectWE.getInt("id"));
                                industrialSectorsItem.setName(objectWE.getString("name"));
                                industrialSectorsArrayList.add(industrialSectorsItem);

                                industrialSectors.add(objectWE.getString("name"));
                            }

                            ArrayAdapter spinner_adapter = new ArrayAdapter(WExperienceActivity.this, android.R.layout.simple_spinner_item, industrialSectors);
                            sectorIndustrialSP.setAdapter(spinner_adapter);

                            //quitar y poner el load Extras cuando se implemnete ese método
                            progressBar.setVisibility(View.INVISIBLE);

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

    public class loadExtras extends AsyncTask<Void,Void, LoadExtras>{

        @Override
        protected LoadExtras doInBackground(Void... params) {
            LoadExtras loadExtras = new LoadExtras();
            try {
                intent = getIntent();
                action = intent.getStringExtra("action");
                if (action.equals("edit")){

                }
                return loadExtras;
            }
            catch(Exception e){

                return loadExtras;
            }
        }

        @Override
        protected void onPostExecute(LoadExtras loadExtras) {
            super.onPostExecute(loadExtras);
        }
    }
}


