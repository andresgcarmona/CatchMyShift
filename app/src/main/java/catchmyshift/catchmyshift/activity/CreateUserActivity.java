package catchmyshift.catchmyshift.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class CreateUserActivity extends AppCompatActivity {

    @BindView(R.id.textEmailAddress) EditText emailTxt;
    @BindView(R.id.textCellphoneNumber) EditText phoneTxt;
    @BindView(R.id.textPassword) EditText passwordTxt;
    @BindView(R.id.textConfirmPassword) EditText confirmPassTxt;
    @BindString(R.string.title_warning) String warning;
    @BindString(R.string.title_setemail) String setEmail;
    @BindString(R.string.title_equalpass) String equalPassword;
    @BindString(R.string.title_setpassword) String setPassword;
    @BindString(R.string.title_setvalidEmail) String setValidEmail;
    String titleLoadingText;
    @BindString(R.string.title_Loading)
    String loadingText;
    @BindString(R.string.title_error_signup)
    String errorSignup;

    @BindString(R.string.dialog_error_data) String errorLoadData;
    private Intent intent;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    //OAUTH REQUEST
    private String URL_DATA_OAUTH = "http://67.205.138.130/oauth/token";
    private String GRANT_TYPE = "client_credentials";
    private String CLIENT_ID = "3";
    private String CLIENT_SECRET = "CT0uLNU8YmacWlnfXsbSpmsEcDhyPsxCXLfTKBXc";

    //GET TOKEN REQUEST
    private String URL_DATA = "http://67.205.138.130/api/signup";
    private String ACCEPT = "application/json";
    private String Bearer = "Bearer ";

    public String user_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAcceptCreate)
    public void CrearUsuario(){
        try {
            Log.e("JMMC_EMAIL:",">"+emailTxt.getText()+"<");
            if(emailTxt.getText().toString().equals("")){
                MyMethods.InfoDialog(CreateUserActivity.this, warning,setEmail).show();
            }
            else{
                String email = emailTxt.getText().toString().trim();
                if(email.matches(emailPattern)){
                    if(!passwordTxt.getText().toString().equals(confirmPassTxt.getText().toString())){
                        MyMethods.InfoDialog(CreateUserActivity.this, warning,equalPassword).show();
                    } else{
                        if(!passwordTxt.getText().toString().equals("")){

                            //EN ESTA PARTE IRÁ EL MÉTODO PARA CREAR EL USUARIO
                            CreateUser();
                        }
                        else {
                            MyMethods.InfoDialog(CreateUserActivity.this, warning,setPassword).show();
                        }
                    }
                }
                else{
                    MyMethods.InfoDialog(CreateUserActivity.this, warning,setValidEmail).show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void CreateUser(){
        intent = getIntent();
        String type = intent.getStringExtra("type");
        if(type.equals("1")){
            user_type = "employee";
        }else{
            user_type = "employer";
        }
        RequestOauthToken();
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
                            RequestSignUp(OAUTH_TOKEN);


                        } catch (JSONException e) {
                            MyMethods.InfoDialog(CreateUserActivity.this, "Error", errorLoadData).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        MyMethods.InfoDialog(CreateUserActivity.this, "Error", errorLoadData).show();
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

    public void RequestSignUp(final String OAUTH_TOKEN) {

        final Dialog progressDialog = new Dialog(CreateUserActivity.this);
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
                            String respuesta = objetUser.getString("response");

                            if (respuesta.equals("success")) {
                                progressDialog.hide();
                                MyMethods.VerifyDialog(CreateUserActivity.this, emailTxt.getText().toString()).show();
                            } else {
                                progressDialog.hide();
                                MyMethods.InfoDialog(CreateUserActivity.this, "Info.", errorSignup).show();
                            }
                        } catch (JSONException e) {
                            progressDialog.hide();
                            MyMethods.InfoDialog(CreateUserActivity.this, "Info.", errorSignup).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("JMMC ERROR", error.getMessage());
                        MyMethods.InfoDialog(CreateUserActivity.this, "Error.", errorSignup).show();
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
                params.put("email", emailTxt.getText().toString());
                params.put("password", passwordTxt.getText().toString());
                params.put("user_type", user_type);
                Log.e("JMMC", "PARAMS");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
