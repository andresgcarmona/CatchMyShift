package catchmyshift.catchmyshift.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.adapter.SearchJobAdapter;
import catchmyshift.catchmyshift.listitem.SearchJobListItem;

public class SearchJobActivity extends AppCompatActivity {

    @BindView(R.id.searchJob_recycleView) RecyclerView searchJobRecyclerView;

    private  RecyclerView.Adapter adapter;
    private List<SearchJobListItem> searchJobListItems;

    private String URL_DATA_SEARCHJOB = "http://67.205.138.130/api/jobs/lists";
    static final int READ_BLOCK_SIZE = 100;

    private  SwipeRefreshLayout refreshJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_job);
        ButterKnife.bind(this);
        searchJobRecyclerView.setHasFixedSize(true);
        searchJobRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchJobListItems = new ArrayList<>();
        refreshJobs = (SwipeRefreshLayout)findViewById(R.id.idjob_searchRefresh);
        refreshJobs.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadFULLTOKEN();
            }
        });
        LoadFULLTOKEN();
    }

    public void LoadFULLTOKEN(){
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
            LoadSearchJobData(FULL_TOKEN.toString());
        }
        catch (Exception e){

        }
    }

    public void LoadSearchJobData(final String FULL_TOKEN){
        refreshJobs.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DATA_SEARCHJOB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            searchJobListItems.clear();
                            JSONArray arrayJobs = new JSONArray(response);
                            for(int i =0; i<arrayJobs.length();i++){
                                JSONObject jobsObject = arrayJobs.getJSONObject(i);
                                JSONObject companyObject = jobsObject.getJSONObject("company");
                                SearchJobListItem item = new SearchJobListItem(
                                        jobsObject.getString("job_name"),
                                        jobsObject.getString("address"),
                                        jobsObject.getString("number_vacancies"),
                                        jobsObject.getString("publication_date"),
                                        jobsObject.getString("start_date"),
                                        jobsObject.getString("end_date"),
                                        jobsObject.getString("start_time"),
                                        jobsObject.getString("end_time"),
                                        jobsObject.getString("salary"),
                                        jobsObject.getString("tasks"),
                                        jobsObject.getString("requirements"),
                                        jobsObject.getString("lat"),
                                        jobsObject.getString("lon"),
                                        companyObject.getString("name"),
                                        companyObject.getString("description"),
                                        companyObject.getString("logo")

                                );
                                Log.e("JMMC",jobsObject.getString("job_name"));
                                Log.e("JMMC",jobsObject.getString("address"));
                                Log.e("JMMC",jobsObject.getString("number_vacancies"));

                                searchJobListItems.add(item);
                            }
                            adapter = new SearchJobAdapter(searchJobListItems,getApplicationContext());
                            searchJobRecyclerView.setAdapter(adapter);
                            refreshJobs.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("JMMC",error.getCause().getMessage());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", FULL_TOKEN);
                Log.e("JMMC", "HEADERS_SearchJob");
                return headers;
            }};

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.showjobsinmap:
                Intent intent = new Intent().setClass(SearchJobActivity.this, MapsUserActivity.class);
                startActivity(intent);
        }
        return(super.onOptionsItemSelected(item));
    }

}
