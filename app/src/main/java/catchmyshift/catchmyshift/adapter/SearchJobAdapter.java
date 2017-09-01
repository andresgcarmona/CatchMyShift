package catchmyshift.catchmyshift.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.activity.JobDetailActivity;
import catchmyshift.catchmyshift.listitem.SearchJobListItem;

/**
 * Created by silve on 31/08/2017.
 */

public class SearchJobAdapter extends RecyclerView.Adapter<SearchJobAdapter.ViewHolder> {

    private List<SearchJobListItem> searchJobListItems;
    private Context context;
    private String avatar;
    String URL_DATA="http://67.205.138.130/";

    public SearchJobAdapter(List<SearchJobListItem> searchJobListItems,  Context context){
        this.searchJobListItems = searchJobListItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_searchjob,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final SearchJobListItem searchJobListItem = searchJobListItems.get(position);
        holder.jobNameTxt.setText(searchJobListItem.getJobName());
        holder.jobAddressTxt.setText(searchJobListItem.getJobAddress());
        holder.jobVacancyTxt.setText(searchJobListItem.getJobVacancyNum());

        avatar=searchJobListItem.getCompanyLogo();
        String comparation = avatar.substring(0,1);
        if(comparation.equals("h")){
            Picasso.with(context).load(avatar).fit().into(holder.companyLogoIV);
        }
        else
        {
            String FULL_URL_AVATAR = URL_DATA.concat(avatar);
            Picasso.with(context).load(FULL_URL_AVATAR).fit().into(holder.companyLogoIV);
        }

        holder.jobLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchJobIntent = new Intent().setClass(context, JobDetailActivity.class);
                searchJobIntent.putExtra("job_name",searchJobListItem.getJobName().toString());
                searchJobIntent.putExtra("address",searchJobListItem.getJobAddress().toString());
                searchJobIntent.putExtra("number_vacancies",searchJobListItem.getJobVacancyNum().toString());
                searchJobIntent.putExtra("publication_date",searchJobListItem.getJobPubDate().toString());

                searchJobIntent.putExtra("start_date",searchJobListItem.getJobStartDate().toString());
                searchJobIntent.putExtra("end_date",searchJobListItem.getJobEndDate().toString());
                searchJobIntent.putExtra("start_time",searchJobListItem.getJobStartTime().toString());
                searchJobIntent.putExtra("end_time",searchJobListItem.getJobEndTime().toString());
                searchJobIntent.putExtra("salary",searchJobListItem.getJobSalary().toString());
                searchJobIntent.putExtra("tasks",searchJobListItem.getJobTask().toString());
                searchJobIntent.putExtra("requirements",searchJobListItem.getJobRequirements().toString());
                searchJobIntent.putExtra("lat",searchJobListItem.getJobLat().toString());
                searchJobIntent.putExtra("lon",searchJobListItem.getJobLong().toString());

                searchJobIntent.putExtra("name", searchJobListItem.getCompanyName().toString());
                searchJobIntent.putExtra("description", searchJobListItem.getCompanyDescription().toString());
                searchJobIntent.putExtra("logo", searchJobListItem.getCompanyLogo().toString());

                searchJobIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(searchJobIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return searchJobListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //define objects
        public TextView jobNameTxt;
        public TextView jobAddressTxt;
        public TextView jobVacancyTxt;
        public ImageView companyLogoIV;
        public LinearLayout jobLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            jobNameTxt = (TextView) itemView.findViewById(R.id.idjob_name);
            jobAddressTxt = (TextView) itemView.findViewById(R.id.idjob_address);
            jobVacancyTxt = (TextView) itemView.findViewById(R.id.idjob_numvacancy);
            companyLogoIV = (ImageView) itemView.findViewById(R.id.idjob_logocompany);
            jobLinearLayout = (LinearLayout) itemView.findViewById(R.id.idjob_LinearLayout);

        }
    }
}
