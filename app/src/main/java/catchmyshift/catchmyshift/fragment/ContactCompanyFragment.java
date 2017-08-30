package catchmyshift.catchmyshift.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.activity.CompaniesActivity;
import catchmyshift.catchmyshift.activity.EditUserActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactCompanyFragment extends Fragment {


    public ContactCompanyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_company, container, false);
        ButterKnife.bind(this,v);
        return v;
    }

    @OnClick(R.id.button_editcompany)
    public void EditUser(){
        Intent intent = new Intent().setClass(getContext(), CompaniesActivity.class);
        startActivity(intent);
    }

}
