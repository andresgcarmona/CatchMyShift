package catchmyshift.catchmyshift.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.fragment.PaymentFragment;
import catchmyshift.catchmyshift.fragment.ProfileFragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserActivity extends AppCompatActivity {

    @BindView(R.id.userImageProfile) CircleImageView imageUser;
    @BindString(R.string.title_selectOption) String selectOption;
    Dialog myDialog;
    Button cameraOption, galleryOpcion;
    TextView cancelOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);
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
                myDialog.hide();
            }
        });

        galleryOpcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.hide();
            }
        });

        cancelOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.hide();
            }
        });

    }

}
