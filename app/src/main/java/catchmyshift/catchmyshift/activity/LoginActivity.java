package catchmyshift.catchmyshift.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONArray;
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
    @BindView(R.id.edittext_password) TextInputEditText password;

    @BindString(R.string.title_Loading)String loadingText;
    @BindString(R.string.loading) String titleLoadingText;
    @BindString(R.string.title_error_userpassword)String errorUserPassText;
    @BindString(R.string.dialog_error_data)String errorLoadData;

    private String URL_DATA = "http://67.205.138.130/api/login";
    private String ACCEPT = "application/json";
    private String AUTHORIZATION = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImVjZTkyZTA2NzdlYjUzYmY5ZjhjMjUxYjNiMTU1YjA3YmRhNmQxZDQ4OTIwYTVhZDM4MDBiYzg2MmEwYzAzNzc4MGM3YWRiMDZmYjY5OGY5In0.eyJhdWQiOiIzIiwianRpIjoiZWNlOTJlMDY3N2ViNTNiZjlmOGMyNTFiM2IxNTViMDdiZGE2ZDFkNDg5MjBhNWFkMzgwMGJjODYyYTBjMDM3NzgwYzdhZGIwNmZiNjk4ZjkiLCJpYXQiOjE1MDM4NzMxNTgsIm5iZiI6MTUwMzg3MzE1OCwiZXhwIjoxNTM1NDA5MTU4LCJzdWIiOiIiLCJzY29wZXMiOltdfQ.ITkGEZWj2ZbvMBiiLKANKWwN6q9qsbqWVtn7u0ry24GDz8J6GXEMehajEJRVDTwGPVVA8xbOL_J3HwsWarJZIE1jMREHdG90jC32v9m2uNoHgGb4Sya1n0eL-pQfwYBY_vrWMpC3uthdMHEYYE40GmHft0CD7JKqt4t1IAnqQa2eabYkoUAsV_F7N8I4pka9xSBZFc20IR0K2rDWMGkN5SXQt4oSbgxVFe1S8NoGxj3VHGkTT_ekP5y_8NxMQjd6UxojdzwE7LVAbix36A6XcqoUiOByUFjbzQILvb5ISEowmU7ZSS8xgvFKKGZyoQURiiKixSysXSgZzzCxVzzoM6P58wDupJ9YQef2vEaIqjMuE2j9njgDavp9vCiQX1tJ7WH-JF__C-1bC5_V7m8KqLGH_FEbBn1UHKlHy0rl4PfB5c0OifBG4KbPRLu1VWRMQSsVJKQGR9ZBSmB0m49gJ2aRRt1VaqzyNVYPpTBClmfXEPvHQRsBmgLCuaobpLOHwbzkQ1loblPBwGtJ2iWuKCNRjQUVxNBC47vnpJwDQE7YGjnKrM2h8tjJRpmyEJ4a-FlNmY_7eyfyGfjMyfXa_dE9ouceZ_34U-AxCSOS52KfwXwOm0qAOENu8_aH7DXqHAJvxgc0Fu7-K1DteBupgDNGteQvYKNDrGVghxyCqoU";

    View currentView;

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
            RequestLogin();
        }
    }

    @OnClick(R.id.text_createAccoutn)
    public void CreateAccount(){
        Intent intent = new Intent().setClass(getApplicationContext(),CreateUserActivity.class);
        startActivity(intent);
    }

    public void RequestLogin(){

        btnLogin.setEnabled(false);
        //MyMethods.LoadingDialog(LoginActivity.this,titleLoadingText,loadingText).show();



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
                            String status = objetUser.getString("status");

                            if (status.equals("200")){
                                JSONObject userObject = objetUser.getJSONObject("user");
                                Log.e("JMMC-USUARIO:",userObject.toString());
                                btnLogin.setEnabled(false);
                                Intent intent = new Intent().setClass(getApplicationContext(), UserActivity.class);

                                intent.putExtra("avatar",userObject.getString("avatar"));
                                intent.putExtra("fullname",userObject.getString("fullname"));
                                intent.putExtra("email", userObject.getString("email"));
                                intent.putExtra("about", userObject.getString("about"));
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                btnLogin.setEnabled(true);
                                MyMethods.InfoDialog(LoginActivity.this,"Info.",errorLoadData).show();
                            }
                        } catch (JSONException e) {
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
                headers.put("Authorization", AUTHORIZATION);
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

    @OnClick(R.id.text_linkedIn)
    public void SignInLinked(){
        LISessionManager.getInstance(getApplicationContext()).init(this, scope(), new AuthListener() {
            @Override
            public void onAuthSuccess() {
                getInfo();
            }

            @Override
            public void onAuthError(LIAuthError error) {

            }
        }, true);
    }

    public static Scope scope(){
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE, Scope.R_EMAILADDRESS);
    }

    private void getInfo(){
        String url = "https://api.linkedin.com/v1/people/~:(id,first-name,last-name,public-profile-url,picture-url,email-address,picture-urls::(original))";
        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(this, url, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse apiResponse) {

                try {
                    JSONObject jsonObject = apiResponse.getResponseDataAsJson();
                    String firstName = jsonObject.getString("firstName");
                    String lastName = jsonObject.getString("lastName");
                    String pictureUrl = jsonObject.getString("pictureUrl");
                    String emailAddress = jsonObject.getString("emailAddress");
                    Log.e("JMMC LinkedIn = ", firstName + " " + lastName + " email = " + emailAddress);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JMMC ERROR", e.getMessage());
                }

            }

            @Override
            public void onApiError(LIApiError LIApiError) {
                Log.e("Error Info: ", LIApiError.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
/*
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
*/

}
