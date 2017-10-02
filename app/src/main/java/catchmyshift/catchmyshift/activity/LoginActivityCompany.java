package catchmyshift.catchmyshift.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;

public class LoginActivityCompany extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_company);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_login_comp)
    public void Login(){
        Intent intent = new Intent().setClass(getApplicationContext(),CompanyActivity.class);
        startActivity(intent);
    }


}
/*
BTN LOGIN
    Intent intent = new Intent().setClass(getApplicationContext(),CompaniesActivity.class);
    startActivity(intent);

BTN CREATE ? <...revisar
    Intent intent = new Intent().setClass(getApplicationContext(),CompanyActivity.class);
    startActivity(intent);

    */