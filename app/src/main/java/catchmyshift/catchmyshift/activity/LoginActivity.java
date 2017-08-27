package catchmyshift.catchmyshift.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.utilities.MyMethods;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.button_login) Button btnLogin;
    @BindView(R.id.text_createAccoutn) TextView createAccount;

    View currentView;
    private String URL_DATA = "http://67.205.138.130/api/user";
    private String ACCEPT = "application/json";
    private String AUTHORIZATION = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImUxYjIwNTJjNjY3MjJlMDQ2OTc0ZDUwOGM3OTY0YTY0NzYxNWYwODk1YzQ3ZWJjMzI3OWI2NWYyYmFhMTFmODI3ZmVmZmJmNmVhMzg3YmVhIn0.eyJhdWQiOiIzIiwianRpIjoiZTFiMjA1MmM2NjcyMmUwNDY5NzRkNTA4Yzc5NjRhNjQ3NjE1ZjA4OTVjNDdlYmMzMjc5YjY1ZjJiYWExMWY4MjdmZWZmYmY2ZWEzODdiZWEiLCJpYXQiOjE1MDM3MTEzOTksIm5iZiI6MTUwMzcxMTM5OSwiZXhwIjoxNTM1MjQ3Mzk5LCJzdWIiOiIiLCJzY29wZXMiOltdfQ.BGDzml2xCc2hxXeuIRY9rutX5bHwYu2fs8SL9RgXKoCU7CsXRxJ-dc5uQ_KNq7dNpZtxmJd_TZhhyX2s8QMTBfed1l_btAMdcOTvmAaSnbSn4iA9rySnG7kjWIH9z3yjnEOBz6ZLhUa_LeKDtzOiUt9V2E7rijw5ovtg7UWUNjS5Wm1ovKijhLcNovpaUN9TIHJ5_Vt4339qrjGxiW2aQZgS4RhEA81kvDVYXJ-UJM9OcdUwaM5LpqeHPZPLDGp2wpJO-XiKTCxKe7A4HD-54fZPWU0W6oHhmD_hU9EXs5-uO4CUKptfWcFUGojym9smV8PLF02mhElb9V1x2RsfNy7yXYa1cwqXSudR-cflRNU35bqUOG4-x4Bw1pSyuY-EtkChs6JzEwPrBzxEXxhBvOBDnGUByRlaX3aaZGKYgPQd-Yrn2VBtnu6rBiuPsTyEOBCqoV9-vyitTOE0lFIV1DshdMrvwHs0bieBDtTsW56dGz3wgCyGRAEka9HempVT7zKEkDWjo-tdRmYpOQ1i1LhBj89UB_G3bmLr8QSCNUkIylK2YRJnPOQno6kxYq0wsUzw6NXC0btqdsHce4-sRlv5bI1ezc5rXT1wzfSGZhn6H4VsIapp7h-6CFyI90lBQtGaWSlf0FyAIMfC7BsHYdPxlhy9vYQwXxjMkA44jOs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        currentView = getWindow().getDecorView().findViewById(android.R.id.content);

        TestingUsers();

    }

    private void TestingUsers() {

        MyMethods.InProgress(currentView,"Loading...",getApplicationContext()).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyMethods.Success(currentView,"Data loaded",getApplicationContext()).show();
                        try {

                            JSONArray arrayUser = new JSONArray(response);

                            for(int i =0; i<arrayUser.length();i++){
                                JSONObject objectUser = arrayUser.getJSONObject(i);

                                Log.e("JMMC_User",objectUser.getString("email"));
                                Log.e("JMMC_Avatar",objectUser.getString("avatar"));
                                Log.e("JMMC_FullName",objectUser.getString("fullname"));
                                Log.e("JMMC_Settings",objectUser.getString("settings"));

                                if (objectUser.getString("settings")==null|| objectUser.getString("settings").equals("null")){
                                    Log.e("JMMC_Settings", "si soy");
                                }
                                else
                                {
                                    Log.e("JMMC_Settings", "no soy");
                                    JSONObject settingsObject = objectUser.getJSONObject("settings");
                                    Log.e("JMMC_EmailPublic", settingsObject.getString("is_email_public"));
                                }

                                Log.e("end","----------------------------------------------------------");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("JMMC",error.getCause().getMessage());
                        MyMethods.Danger(currentView,"ERROR to load data",getApplicationContext()).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap < String, String > headers = new HashMap< String, String >();
                headers.put("Accept",ACCEPT);
                headers.put("Authorization",AUTHORIZATION);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @OnClick(R.id.button_login)
    public void Login(){
        btnLogin.setEnabled(false);
        Intent intent = new Intent().setClass(getApplicationContext(), UserActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.text_createAccoutn)
    public void CreateAccount(){
        Intent intent = new Intent().setClass(getApplicationContext(),CreateUserActivity.class);
        startActivity(intent);
    }

}
