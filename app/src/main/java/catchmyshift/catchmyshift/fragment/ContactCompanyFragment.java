package catchmyshift.catchmyshift.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.activity.CompaniesActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactCompanyFragment extends Fragment {

    @BindView(R.id.idcompany_name) TextView companyName;
    @BindView(R.id.idcompany_address) TextView companyAddress;
    @BindView(R.id.idcompany_logo) ImageView companyLogo;
    @BindView(R.id.idcompany_cover) ImageView companyCover;

    static final int READ_BLOCK_SIZE = 100;
    String URL_DATA="http://67.205.138.130/";
    private String URL_DATAC = "http://67.205.138.130/api/company";

    public ContactCompanyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_company, container, false);
        ButterKnife.bind(this,v);

        LoadToken();
        return v;
    }

    @OnClick(R.id.button_editcompany)
    public void EditUser(){
        Intent intent = new Intent().setClass(getContext(), CompaniesActivity.class);
        startActivity(intent);
    }

    public void LoadToken(){
        try {
            FileInputStream fileIn = getActivity().openFileInput("cms.sm");
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
            RequestCompanyInformation(FULL_TOKEN.toString());

        }
        catch (Exception e){
            Log.e("JMCC ERROR", e.getMessage());
        }
    }

    public void RequestCompanyInformation(final String FULL_TOKEN){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATAC,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {

                            JSONArray arrayC = new JSONArray(response);
                            for(int i = 0; i <arrayC.length(); i++){
                                JSONObject objectC = arrayC.getJSONObject(i);

                                companyName.setText(objectC.getString("name"));
                                companyAddress.setText(objectC.getString("address"));

                                String comparation = objectC.getString("logo").substring(0,1);

                                if(comparation.equals("h")){
                                    Picasso.with(getContext()).load(objectC.getString("logo")).fit().into(companyLogo);
                                }
                                else
                                {
                                    String FULL_URL_LOGO = URL_DATA.concat(objectC.getString("logo"));
                                    Picasso.with(getContext()).load(FULL_URL_LOGO).fit().into(companyLogo);
                                }

                                comparation = objectC.getString("cover").substring(0,1);

                                if(comparation.equals("h")){
                                    Picasso.with(getContext()).load(objectC.getString("cover")).fit().into(companyCover);
                                }
                                else
                                {
                                    String FULL_URL_COVER = URL_DATA.concat(objectC.getString("cover"));
                                    Picasso.with(getContext()).load(FULL_URL_COVER).fit().into(companyCover);
                                }

                            }

                        } catch (JSONException e) {
                            Log.e("JMMC_USER",e.getMessage());
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {}

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", FULL_TOKEN);
                Log.e("JMMC", "HEADERS_USERACTIVITY");
                return headers;
            }};

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
