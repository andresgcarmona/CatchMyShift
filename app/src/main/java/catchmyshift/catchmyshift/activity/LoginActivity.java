package catchmyshift.catchmyshift.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.utilities.MyMethods;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.button_login) Button btnLogin;
    @BindView(R.id.text_createAccoutn) TextView createAccount;
    @BindView(R.id.edittext_email) TextInputEditText email;
    @BindString(R.string.title_Loading)String loadingText;
    @BindView(R.id.edittext_password) TextInputEditText password;
    @BindString(R.string.loading) String titleLoadingText;
    @BindString(R.string.title_error_userpassword)String errorUserPassText;
    @BindString(R.string.dialog_error_data)String errorLoadData;

    View currentView;

    //OAUTH REQUEST
    private String URL_DATA_OAUTH="http://67.205.138.130/oauth/token";
    private String GRANT_TYPE = "client_credentials";
    private String CLIENT_ID = "3";
    private String CLIENT_SECRET= "CT0uLNU8YmacWlnfXsbSpmsEcDhyPsxCXLfTKBXc";


    //GET TOKEN REQUEST
    private String URL_DATA = "http://67.205.138.130/api/login";
    private String ACCEPT = "application/json";

    //VALIDATE USER REQUEST
    private String URL_DATAUSER = "http://67.205.138.130/api/user";
    private String Bearer = "Bearer ";
    private String FULL_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        currentView = getWindow().getDecorView().findViewById(android.R.id.content);

    }


    @OnClick(R.id.button_login)
    public void Login(){

        if(email.getText().equals("")||password.getText().equals("")){
            MyMethods.InfoDialog(LoginActivity.this,"Advertencia","los campos email y contraseña no deben estar vacíos");
        }
        else {
            RequestOauthToken();
        }
    }

    @OnClick(R.id.text_createAccoutn)
    public void CreateAccount(){
        Intent intent = new Intent().setClass(getApplicationContext(),CreateUserActivity.class);
        startActivity(intent);
    }


    public void RequestOauthToken(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DATA_OAUTH,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            String OAUTH_TOKEN;
                            JSONObject objetUser = new JSONObject(response);
                            String access_token = objetUser.getString("access_token");
                            OAUTH_TOKEN = Bearer.concat(access_token);
                            Log.e("JMMC_OAUTHtoken",OAUTH_TOKEN);
                            //llamar metodo request_Login
                            RequestLogin(OAUTH_TOKEN);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}

                }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("grant_type", GRANT_TYPE );
                params.put("client_id", CLIENT_ID );
                params.put("client_secret", CLIENT_SECRET );
                params.put("scope", "");


                Log.e("JMMC", "HEADERS_VERYFIED_OAUTH");

                return params;
            }};

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void RequestLogin(final String OAUTH_TOKEN){

        btnLogin.setEnabled(false);

        final Dialog progressDialog = new Dialog(LoginActivity.this);
        progressDialog.setContentView(R.layout.loading_dialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView titledialog = (TextView) progressDialog.findViewById(R.id.titleDialogP);
        titledialog.setText(titleLoadingText);
        TextView contentDialog = (TextView) progressDialog.findViewById(R.id.contentDialogP);
        contentDialog.setText(loadingText);
        progressDialog.setCancelable(false);
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DATA,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject objetUser = new JSONObject(response);
                            String token = objetUser.getString("token");

                            if (!token.equals("null")){
                                FULL_TOKEN = Bearer.concat(token);
                                Log.e("JMMC_TOKEN ", FULL_TOKEN);

                                ValidateUser(FULL_TOKEN);

                            }
                            else
                            {
                                btnLogin.setEnabled(true);
                                MyMethods.InfoDialog(LoginActivity.this,"Info.",errorLoadData).show();
                            }
                        } catch (JSONException e) {
                            MyMethods.InfoDialog(LoginActivity.this,"Info.",errorLoadData).show();
                            btnLogin.setEnabled(true);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        btnLogin.setEnabled(true);
                        MyMethods.InfoDialog(LoginActivity.this,"Error.",errorUserPassText).show();
                        progressDialog.hide();
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
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                Log.e("JMMC", "PARAMS");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void ValidateUser(final String FULL_TOKEN){


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATAUSER,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {

                            JSONObject objetUser = new JSONObject(response);
                            //String user = objetUser.getString("avatar");


                            Log.e("JMMC",objetUser.toString());
                            Log.e("JMMC_AVATAR",objetUser.getString("avatar"));

                            btnLogin.setEnabled(false);
                            Intent intent = new Intent().setClass(getApplicationContext(), UserActivity.class);

                            intent.putExtra("FULL_TOKEN",FULL_TOKEN);
                            intent.putExtra("avatar",objetUser.getString("avatar"));
                            intent.putExtra("fullname",objetUser.getString("fullname"));
                            intent.putExtra("email", objetUser.getString("email"));
                            intent.putExtra("about", objetUser.getString("about"));
                            startActivity(intent);
                            finish();

                        } catch (JSONException e) {
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
                Log.e("JMMC", "HEADERS_VERYFIED");
                return headers;
            }};

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
