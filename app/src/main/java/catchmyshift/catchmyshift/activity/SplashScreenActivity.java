package catchmyshift.catchmyshift.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.q42.android.scrollingimageview.ScrollingImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindString;
import butterknife.ButterKnife;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.utilities.MyMethods;

public class SplashScreenActivity extends AppCompatActivity {

    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 3000;

    private SharedPreferences prefs;

    @BindString(R.string.loading)
    String titleLoadingText;
    @BindString(R.string.title_Loading)
    String loadingText;
    @BindString(R.string.title_error_userpassword)
    String errorUserPassText;
    @BindString(R.string.dialog_error_data)
    String errorLoadData;

    //OAUTH REQUEST
    private String URL_DATA_OAUTH = "http://67.205.138.130/oauth/token";
    private String GRANT_TYPE = "client_credentials";
    private String CLIENT_ID = "3";
    private String CLIENT_SECRET = "CT0uLNU8YmacWlnfXsbSpmsEcDhyPsxCXLfTKBXc";

    //GET TOKEN REQUEST
    private String URL_DATA = "http://67.205.138.130/api/login";
    private String ACCEPT = "application/json";

    //VALIDATE USER REQUEST
    private String URL_DATAUSER = "http://67.205.138.130/api/user";
    private String Bearer = "Bearer ";
    private String FULL_TOKEN;

    public String userEmail, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        try {
            ScrollingImageView scrollingBackground = (ScrollingImageView) findViewById(R.id.scrolling_background);
            scrollingBackground.stop();
            scrollingBackground.start();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                userEmail = getUserMailPreferences();
                userPassword = getUserPassPreferences();
                // Start the next activity

                if(TextUtils.isEmpty(userEmail) && TextUtils.isEmpty(userPassword)){
                    Intent mainIntent = new Intent().setClass(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                }else {

                    RequestOauthToken();
                }
                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

        ButterKnife.bind(this);
    }

    public String getUserMailPreferences(){
        return prefs.getString("email", "");
    }

    public String getUserPassPreferences(){
        return prefs.getString("password", "");
    }

    public void RequestOauthToken() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DATA_OAUTH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String OAUTH_TOKEN;
                            JSONObject objetUser = new JSONObject(response);
                            String access_token = objetUser.getString("access_token");
                            OAUTH_TOKEN = Bearer.concat(access_token);
                            Log.e("JMMC_OAUTHtoken", OAUTH_TOKEN);
                            //llamar metodo request_Login
                            RequestLogin(OAUTH_TOKEN);


                        } catch (JSONException e) {
                            MyMethods.InfoDialog(SplashScreenActivity.this, "Error", errorLoadData).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MyMethods.InfoDialog(SplashScreenActivity.this, "Error", errorLoadData).show();
                    }
                }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("grant_type", GRANT_TYPE);
                params.put("client_id", CLIENT_ID);
                params.put("client_secret", CLIENT_SECRET);
                params.put("scope", "");


                Log.e("JMMC", "HEADERS_VERYFIED_OAUTH");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void RequestLogin(final String OAUTH_TOKEN) {
        /*final Dialog progressDialog = new Dialog(SplashScreenActivity.this);
        progressDialog.setContentView(R.layout.loading_dialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView titledialog = (TextView) progressDialog.findViewById(R.id.titleDialogP);
        titledialog.setText(titleLoadingText);
        TextView contentDialog = (TextView) progressDialog.findViewById(R.id.contentDialogP);
        contentDialog.setText(loadingText);
        progressDialog.setCancelable(false);
        progressDialog.show();

*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objetUser = new JSONObject(response);
                            String token = objetUser.getString("token");

                            if (!token.equals("null")) {
                                FULL_TOKEN = Bearer.concat(token);
                                Log.e("JMMC_TOKEN ", FULL_TOKEN);
                                SaveToken(FULL_TOKEN);
                                SaveOAuthToken(OAUTH_TOKEN);
                                Intent intent = new Intent().setClass(getApplicationContext(), UserActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                MyMethods.InfoDialog(SplashScreenActivity.this, "Info.", errorLoadData).show();
                            }
                        } catch (JSONException e) {
                            MyMethods.InfoDialog(SplashScreenActivity.this, "Info.", errorLoadData).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MyMethods.InfoDialog(SplashScreenActivity.this, "Error.", errorUserPassText).show();
                        //progressDialog.hide();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", ACCEPT);
                headers.put("Authorization", OAUTH_TOKEN);
                Log.e("JMMC", "HEADERS");
                return headers;
            }

            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("email", userEmail);
                params.put("password", userPassword);
                Log.e("JMMC", "PARAMS");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void SaveToken(String FULL_TOKEN) {
        try {
            FileOutputStream fileout=openFileOutput("cms.sm", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(FULL_TOKEN.toString());
            outputWriter.close();
        }
        catch (Exception e) {

        }

    }

    public void SaveOAuthToken(String FULL_TOKEN) {
        try {
            FileOutputStream fileout=openFileOutput("cmsoa.sm", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(FULL_TOKEN.toString());
            outputWriter.close();
        }
        catch (Exception e) {

        }

    }


}

