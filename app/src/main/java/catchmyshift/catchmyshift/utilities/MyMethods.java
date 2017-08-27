package catchmyshift.catchmyshift.utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import catchmyshift.catchmyshift.R;

/**
 * Created by SILVER on 24/07/2017.
 */

public class MyMethods {

    public static Snackbar  Success(View view, String data, Context context){

        final Snackbar snackbar = Snackbar.make(view, "       "+data, Snackbar.LENGTH_LONG );
        View subview = snackbar.getView();

        TextView snackbar_text = (TextView) subview.findViewById(android.support.design.R.id.snackbar_text);
        snackbar_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.successvd,0,0,0);
        snackbar_text.setGravity(Gravity.CENTER);
        subview.setBackgroundColor(ContextCompat.getColor(context,R.color.colorSnackSuccess));
        snackbar.setAction("x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    snackbar.dismiss();
            }
        });
        snackbar.setActionTextColor(Color.WHITE);
        return snackbar;
    }


    public static Snackbar  Danger(View view, String data, Context context){

        final Snackbar snackbar = Snackbar.make(view, "       "+data, Snackbar.LENGTH_LONG );
        View subview = snackbar.getView();

        TextView snackbar_text = (TextView) subview.findViewById(android.support.design.R.id.snackbar_text);
        snackbar_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.warningvd,0,0,0);
        snackbar_text.setGravity(Gravity.CENTER);
        subview.setBackgroundColor(ContextCompat.getColor(context,R.color.colorSnackDanger));
        snackbar.setAction("x", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.setActionTextColor(Color.WHITE);
        return snackbar;
    }


    public static Snackbar  InProgress(View view, String data, Context context){

        Snackbar snackbar = Snackbar.make(view, "       "+data, Snackbar.LENGTH_INDEFINITE );
        View subview = snackbar.getView();

        ProgressBar progressBar = new ProgressBar(context);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
        ViewGroup viewGroup = (ViewGroup) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text).getParent();
        viewGroup.addView(progressBar,0);
        return snackbar;
    }

    public static Dialog InfoDialog(Context context, String title, String content){

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.info_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView titledialog = (TextView) dialog.findViewById(R.id.titleDialog);
        titledialog.setText(title);
        TextView contentDialog = (TextView) dialog.findViewById(R.id.contentDialog);
        contentDialog.setText(content);

        TextView okDialog = (TextView) dialog.findViewById(R.id.okDialog);
        okDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        return dialog;
    }

    public static Dialog LoadingDialog(Context context, String title, String content){

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView titledialog = (TextView) dialog.findViewById(R.id.titleDialogP);
        titledialog.setText(title);
        TextView contentDialog = (TextView) dialog.findViewById(R.id.contentDialogP);
        contentDialog.setText(content);

        return dialog;
    }
}
