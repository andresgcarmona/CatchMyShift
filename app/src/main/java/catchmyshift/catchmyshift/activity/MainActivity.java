package catchmyshift.catchmyshift.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button_buscartrabajo) Button btnSearchJob;
    @BindView(R.id.button_publicvacante) Button btnPublishVacancy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_buscartrabajo)
    public void SearchJob(){
        Intent intent = new Intent().setClass(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_publicvacante)
    public void PublishVacancy() {
        Intent intent = new Intent().setClass(getApplicationContext(), LoginActivityCompany.class);
        startActivity(intent);
    }

}
