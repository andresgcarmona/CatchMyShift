package catchmyshift.catchmyshift.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

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

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
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
        MyMethods.VerifyDialog(CreateUserActivity.this, emailTxt.getText().toString()).show();
    }
}
