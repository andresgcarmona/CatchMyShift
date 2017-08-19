package catchmyshift.catchmyshift.fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import catchmyshift.catchmyshift.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublishJobCompanyFragment extends Fragment {

    public PublishJobCompanyFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_publish_job_company, container, false);
    }

}
