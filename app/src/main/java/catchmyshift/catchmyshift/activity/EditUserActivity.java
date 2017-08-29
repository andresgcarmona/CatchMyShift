package catchmyshift.catchmyshift.activity;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.utilities.MyMethods;
import de.hdodenhof.circleimageview.CircleImageView;


public class EditUserActivity extends AppCompatActivity {

    @BindView(R.id.userImageProfile) CircleImageView imageUser;
    @BindString(R.string.title_selectOption) String selectOption;
    @BindString(R.string.permission) String permissions;
    @BindString(R.string.permisonSuccess) String permissionSuccess;
    @BindString(R.string.permisonFail) String permissionFail;

    Dialog myDialog;
    Button cameraOption, galleryOpcion;
    TextView cancelOption;
    private Bitmap bitmap;
    private final int GALLERY_REQUEST = 0;
    private final int CAMERA_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);

        LoadDataUser();
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
                Toast.makeText(this, ""+bitmap, Toast.LENGTH_SHORT).show();
                //Setting the Bitmap to ImageView
                imageUser.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK ){
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
                imageUser.setImageBitmap(bitmap);
            }
        }
    }

    public String getStringImage(Bitmap bitmap){
        Log.i("MyHitesh",""+ bitmap);
        ByteArrayOutputStream baos = new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);


        return temp;
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
