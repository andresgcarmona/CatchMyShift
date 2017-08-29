package catchmyshift.catchmyshift.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.utilities.MyMethods;

public class CreateUserActivity extends AppCompatActivity {

    @BindView(R.id.textEmailAddress) EditText emailTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAcceptCreate)
    public void CrearUsuario(){
        MyMethods.VerifyDialog(CreateUserActivity.this, emailTxt.getText().toString()).show();
    }
}
