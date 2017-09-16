package catchmyshift.catchmyshift.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.fragment.ContactCompanyFragment;
import catchmyshift.catchmyshift.utilities.MyMethods;

public class LoginActivity extends AppCompatActivity {

    private FragmentManager manager;

    private SharedPreferences prefs;

    @BindView(R.id.button_login)
    Button btnLogin;
    @BindView(R.id.text_createAccoutn)
    TextView createAccount;
    @BindView(R.id.edittext_email)
    TextInputEditText email;
    @BindView(R.id.edittext_password)
    TextInputEditText password;
    @BindView(R.id.idRemember_me)
    Switch rememberMe;
    @BindString(R.string.loading)
    String titleLoadingText;
    @BindString(R.string.title_Loading)
    String loadingText;
    @BindString(R.string.title_error_userpassword)
    String errorUserPassText;
    @BindString(R.string.dialog_error_data)
    String errorLoadData;
    @BindString(R.string.title_warning)String warning;
    @BindString(R.string.title_userpassMust)String mustuserPassword;

    View currentView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        ButterKnife.bind(this);
        currentView = getWindow().getDecorView().findViewById(android.R.id.content);

    }


    @OnClick(R.id.button_login)
    public void Login() {

        Log.e("JMMMTEXTO",">"+email.getText().toString()+"<"+">"+password.getText().toString()+"<");
        if (!email.getText().toString().equals("") && !password.getText().toString().equals("")) {
            RequestOauthToken();
        }
        else {
            MyMethods.InfoDialog(LoginActivity.this, warning, mustuserPassword).show();
        }
    }

    @OnClick(R.id.text_createAccoutn)
    public void CreateAccount() {
        Intent intent = new Intent().setClass(getApplicationContext(), CreateUserActivity.class);
        startActivity(intent);
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
                            SaveOAuthToken(OAUTH_TOKEN);
                            //llamar metodo request_Login
                            RequestLogin(OAUTH_TOKEN);


                        } catch (JSONException e) {
                            MyMethods.InfoDialog(LoginActivity.this, "Error", errorLoadData).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MyMethods.InfoDialog(LoginActivity.this, "Error", errorLoadData).show();
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
                                btnLogin.setEnabled(false);

                                saveOnPreferences(email.getText().toString(), password.getText().toString());

                                Intent intent = new Intent().setClass(getApplicationContext(), UserActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                btnLogin.setEnabled(true);
                                MyMethods.InfoDialog(LoginActivity.this, "Info.", errorLoadData).show();
                            }
                        } catch (JSONException e) {
                            MyMethods.InfoDialog(LoginActivity.this, "Info.", errorLoadData).show();
                            btnLogin.setEnabled(true);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        btnLogin.setEnabled(true);
                        MyMethods.InfoDialog(LoginActivity.this, "Error.", errorUserPassText).show();
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

    @OnClick(R.id.text_linkedIn)
    public void SignInLinked() {
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

    public static Scope scope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE, Scope.R_EMAILADDRESS);
    }

    private void getInfo() {
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



    private void saveOnPreferences(String email, String password){
        if (rememberMe.isChecked()){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email",email);
            editor.putString("password",password);
            editor.apply();
        }
    }
}



/*
    private void TestingUsers() {


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
*/