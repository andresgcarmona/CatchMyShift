package catchmyshift.catchmyshift.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.listitem.WorkExperienceListItem;

/**
 * Created by melli on 02/09/2017.
 */

public class WorkExperienceAdapter extends RecyclerView.Adapter<WorkExperienceAdapter.ViewHolder> {

    List<WorkExperienceListItem> workExperienceListItems;
    private Context context;

    public WorkExperienceAdapter(List<WorkExperienceListItem> workExperienceListItems, Context context){
        this.workExperienceListItems = workExperienceListItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_workexperience,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WorkExperienceListItem workExperienceListItem = workExperienceListItems.get(position);

        holder.textViewJobPosition.setText(workExperienceListItem.getJobPosition());
        holder.textViewJobCompany.setText(workExperienceListItem.getJobCompany());
        holder.textViewJobDate.setText(workExperienceListItem.getJobDate());
    }

    @Override
    public int getItemCount() {
        return workExperienceListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewJobPosition;
        public TextView textViewJobCompany;
        public TextView textViewJobDate;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewJobPosition = (TextView) itemView.findViewById(R.id.idjob_position);
            textViewJobCompany = (TextView) itemView.findViewById(R.id.idjob_company);
            textViewJobDate = (TextView) itemView.findViewById(R.id.idjob_date);
        }
    }
}
