package catchmyshift.catchmyshift.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.activity.CompanyActivity;
import catchmyshift.catchmyshift.activity.CreateCompanyActivity;

public class LoginActivityCompany extends AppCompatActivity {
    private Button btnLogin;
    private TextView txtCreateAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_company);

        btnLogin = (Button) findViewById(R.id.button_login);
        txtCreateAccount = (TextView) findViewById(R.id.textview_createaccount);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent().setClass(getApplicationContext(),CompanyActivity.class);
                    startActivity(intent);
                }

                catch (Exception e){
                    Log.e("JMMC",e.getMessage());
                }
            }
        });
        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent().setClass(getApplicationContext(),CreateCompanyActivity.class);
                    startActivity(intent);
                }

                catch (Exception e){
                    Log.e("JMMC",e.getMessage());
                }
            }
        });
    }
}
