package catchmyshift.catchmyshift.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.listitem.AcademicLevelListItem;

/**
 * Created by melli on 03/09/2017.
 */

public class AcademicLevelAdapter extends RecyclerView.Adapter<AcademicLevelAdapter.ViewHolder> {

    List<AcademicLevelListItem> academicLevelListItems;
    Context context;

    public AcademicLevelAdapter(List<AcademicLevelListItem> academicLevelListItems, Context context){
        this.academicLevelListItems = academicLevelListItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_academiclevels,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AcademicLevelListItem academicLevelListItem = academicLevelListItems.get(position);

        holder.textViewAcademicDegree.setText(academicLevelListItem.getAcademicDegree());
        holder.textViewAcademicInstitution.setText(academicLevelListItem.getAcademicInstitution());
        holder.textViewAcademicDate.setText(academicLevelListItem.getAcademicYear());
    }

    @Override
    public int getItemCount() {
        return academicLevelListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewAcademicDegree;
        public TextView textViewAcademicInstitution;
        public TextView textViewAcademicDate;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewAcademicDegree = (TextView) itemView.findViewById(R.id.idacademic_degree);
            textViewAcademicInstitution = (TextView) itemView.findViewById(R.id.idacademic_institution);
            textViewAcademicDate = (TextView) itemView.findViewById(R.id.idacademic_date);
        }
    }
}
