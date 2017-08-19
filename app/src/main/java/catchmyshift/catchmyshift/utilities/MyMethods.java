package catchmyshift.catchmyshift.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
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
        snackbar_text.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dangervd,0,0,0);
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
}
