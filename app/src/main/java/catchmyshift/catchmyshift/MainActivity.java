package catchmyshift.catchmyshift;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnSearchJob, btnPublishVacancy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        btnSearchJob = (Button) findViewById(R.id.button_buscartrabajo);
        btnPublishVacancy = (Button) findViewById(R.id.button_publicvacante);

        btnSearchJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setClass(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        btnPublishVacancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Función pendiente", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
