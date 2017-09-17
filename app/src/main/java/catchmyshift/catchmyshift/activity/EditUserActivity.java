package catchmyshift.catchmyshift.activity;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.utilities.MyMethods;
import de.hdodenhof.circleimageview.CircleImageView;


public class EditUserActivity extends AppCompatActivity {

    @BindView(R.id.userImageProfile) CircleImageView imageUser;
    @BindView(R.id.progressImage) ProgressBar progressImage;
    @BindString(R.string.title_selectOption) String selectOption;
    @BindString(R.string.permission) String permissions;
    @BindString(R.string.permisonSuccess) String permissionSuccess;
    @BindString(R.string.permisonFail) String permissionFail;
    @BindView(R.id.idfecha_nac)TextView fechaNacimiento;

    Dialog myDialog;
    Button cameraOption, galleryOpcion;
    TextView cancelOption;
    private Bitmap bitmap;
    private final int GALLERY_REQUEST = 0;
    private final int CAMERA_REQUEST=1;
    private int mYear, mMonth, mDay;
    String URL_IMAGE = "http://67.205.138.130/api/user/update-avatar";
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);

        LoadDataUser();
    }

    @OnClick(R.id.idfecha_nac)
    public void SetBirthday(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        fechaNacimiento.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.cameraUserProfile)
    public void profilePic(){
        myDialog = new Dialog(EditUserActivity.this);
        myDialog.setContentView(R.layout.cameragallery_dialog);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        cameraOption = (Button) myDialog.findViewById(R.id.idcameraOption);
        galleryOpcion = (Button) myDialog.findViewById(R.id.idgalleryOption);
        cancelOption = (TextView) myDialog.findViewById(R.id.idcancelOption);

        cameraOption.setEnabled(true);
        galleryOpcion.setEnabled(true);
        cancelOption.setEnabled(true);

        cameraOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkPermissionCAM()) {

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    myDialog.hide();

                } else {
                    requestPermissionCAM();
                }
            }
        });

        galleryOpcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermission()) {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"),GALLERY_REQUEST);
                    myDialog.hide();

                } else {
                    requestPermission();
                }
            }
        });

        cancelOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.hide();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && null !=data && data.getData()!=null){
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageUser.setImageBitmap(bitmap);
                new UpImage().execute();

                //UploadImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK ){
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
                imageUser.setImageBitmap(bitmap);
                new UpImage().execute();
                //UploadImage();
            }
        }
    }

    public class UpImage extends AsyncTask<Void,Void,String>{
        @Override
        protected void onPreExecute() {
            progressImage.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            String temp = Base64.encodeToString(b, Base64.DEFAULT);
            String s = "";
            for (int i = 0; i<= 10000; i++){
                s += "Hola " + i;
            }
            return temp;
        }

        @Override
        protected void onPostExecute(String imageConverted) {
            //ReadToken(imageConverted);

            //eliminar esta linea y descomentar ReadToken
        progressImage.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Image loaded",Toast.LENGTH_LONG).show();
        }
    }

    public void  ReadToken(String imageConverted){

        try {
            FileInputStream fileIn = openFileInput("cms.sm");
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String FULL_TOKEN = "";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                FULL_TOKEN += readstring;
            }
            InputRead.close();
            UploadImageRequest(FULL_TOKEN.toString(), imageConverted);

        }
        catch (Exception e){

        }
    }

    public void UploadImageRequest(final String FULL_TOKEN, final String imageConverted){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_IMAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.getString("avatar").equals("PONER LO QUE VA A IR AQUI EN CASO DE QUE SEA CORRECTA LA INSERCIÓN ")){
                        progressImage.setVisibility(View.INVISIBLE);
                    }

                }
                catch (JSONException e){
                    Log.e("AVATAR_ERROR",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("JMMC", error.getMessage());
                Toast.makeText(EditUserActivity.this,"oh rayos..." + error.getMessage(),Toast.LENGTH_LONG ).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("avatar",imageConverted);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                headers.put("Authorization", FULL_TOKEN);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            myDialog.hide();
            MyMethods.InfoDialog(EditUserActivity.this,"Info.",permissions).show();

        } else {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_REQUEST);

        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermissionCAM() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            MyMethods.InfoDialog(EditUserActivity.this,"Info.",permissions).show();

        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);

        }
    }


    private boolean checkPermissionCAM() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MyMethods.InfoDialog(EditUserActivity.this,"Info.",permissionSuccess).show();
                } else {
                    MyMethods.InfoDialog(EditUserActivity.this,"Info.",permissionFail).show();
                }
                break;

            case CAMERA_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MyMethods.InfoDialog(EditUserActivity.this,"Info.",permissionSuccess).show();
                } else {
                    MyMethods.InfoDialog(EditUserActivity.this,"Info.",permissionFail).show();
                }
                break;
        }
    }

    public void LoadDataUser() {
        Intent intent = getIntent();
        String avatar = intent.getStringExtra("avatar");
        Picasso.with(getApplicationContext()).load(avatar).into(imageUser);
        Log.e("JMMC_INTENTAVATAR",avatar);
    }
}
