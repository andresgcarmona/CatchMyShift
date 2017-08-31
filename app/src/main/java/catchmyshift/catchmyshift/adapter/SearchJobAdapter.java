package catchmyshift.catchmyshift.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.listitem.SearchJobListItem;

/**
 * Created by silve on 31/08/2017.
 */

public class SearchJobAdapter extends RecyclerView.Adapter<SearchJobAdapter.ViewHolder> {

    private List<SearchJobListItem> searchJobListItems;
    private Context context;

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
    public void onBindViewHolder(ViewHolder holder, int position) {

        SearchJobListItem searchJobListItem = searchJobListItems.get(position);
        holder.jobNameTxt.setText(searchJobListItem.getJobName());
        holder.jobAddressTxt.setText(searchJobListItem.getJobAddress());
        holder.jobVacancyTxt.setText(searchJobListItem.getJobVacancyNum());

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

        public ViewHolder(View itemView) {
            super(itemView);
            jobNameTxt = (TextView) itemView.findViewById(R.id.idjob_name);
            jobAddressTxt = (TextView) itemView.findViewById(R.id.idjob_address);
            jobVacancyTxt = (TextView) itemView.findViewById(R.id.idjob_numvacancy);

        }
    }
}
