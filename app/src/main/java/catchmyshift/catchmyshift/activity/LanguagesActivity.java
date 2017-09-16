package catchmyshift.catchmyshift.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.adapter.SpinnerAdapter;

public class LanguagesActivity extends AppCompatActivity {

    @BindView(R.id.sp_lenguaje)
    Spinner language;
    @BindView(R.id.sp_lenguaje_nivel)
    Spinner languageLevel;
    private String URL_DATAL = "http://67.205.138.130/api/language";
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languages);

        ButterKnife.bind(this);
        LoadToken();
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
            RequestLanguage(FULL_TOKEN.toString());
            fillLanguageLevelsSpinner();

        }
        catch (Exception e){
            Log.e("JMCC ERROR", e.getMessage());
        }
    }

    public void RequestLanguage(final String FULL_TOKEN){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATAL,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {

                            JSONArray arrayWE = new JSONArray(response);

                            ArrayList<SpinnerAdapter> languagesArrayList = new ArrayList<SpinnerAdapter>();
                            ArrayList<String> languages = new ArrayList<String>();


                            for(int i = 0; i < arrayWE.length(); i++)
                            {
                                JSONObject objectWE = arrayWE.getJSONObject(i);

                                SpinnerAdapter languagesItem = new SpinnerAdapter();
                                languagesItem.setId(objectWE.getInt("id"));
                                languagesItem.setName(objectWE.getString("name"));
                                languagesArrayList.add(languagesItem);

                                languages.add(objectWE.getString("name"));
                            }

                            ArrayAdapter spinner_adapter = new ArrayAdapter(LanguagesActivity.this, android.R.layout.simple_spinner_item, languages);
                            language.setAdapter(spinner_adapter);

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

    public void fillLanguageLevelsSpinner(){
        ArrayList<String> languageLevels = new ArrayList<String>();

        languageLevels.add("Basic");
        languageLevels.add("Intermediate");
        languageLevels.add("Fluent");

        ArrayAdapter spinner_adapter = new ArrayAdapter(LanguagesActivity.this, android.R.layout.simple_spinner_item, languageLevels);
        languageLevel.setAdapter(spinner_adapter);
    }
}
