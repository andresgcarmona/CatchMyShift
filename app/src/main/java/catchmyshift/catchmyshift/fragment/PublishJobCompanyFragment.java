package catchmyshift.catchmyshift.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
