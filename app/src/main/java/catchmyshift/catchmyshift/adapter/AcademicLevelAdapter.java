package catchmyshift.catchmyshift.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.activity.EducationActivity;
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
        final AcademicLevelListItem academicLevelListItem = academicLevelListItems.get(position);

        holder.textViewAcademicDegree.setText(academicLevelListItem.getAcademicDegreeConcat());
        holder.textViewAcademicInstitution.setText(academicLevelListItem.getAcademicInstitution());
        holder.textViewAcademicDate.setText(academicLevelListItem.getAcademicYear());

        holder.academicLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent academicLevelEdit = new Intent().setClass(context, EducationActivity.class);
                academicLevelEdit.putExtra("action","edit");

                academicLevelEdit.putExtra("academic_id", academicLevelListItem.getAcademicId().toString());
                academicLevelEdit.putExtra("academic_user_id", academicLevelListItem.getAcademicUserId().toString());
                academicLevelEdit.putExtra("academic_degree_id", academicLevelListItem.getAcademicDegreeId().toString());
                academicLevelEdit.putExtra("academic_degree_status_id", academicLevelListItem.getAcademicDegreeStatusId().toString());
                academicLevelEdit.putExtra("academic_degree_name", academicLevelListItem.getAcademicDegreeName().toString());
                academicLevelEdit.putExtra("academic_degree_status_name", academicLevelListItem.getAcademicDegreeStatusName().toString());
                academicLevelEdit.putExtra("academic_degree_concat", academicLevelListItem.getAcademicDegreeConcat().toString());
                academicLevelEdit.putExtra("academic_institution", academicLevelListItem.getAcademicInstitution().toString());
                academicLevelEdit.putExtra("academic_start_year", academicLevelListItem.getAcademicStartYear().toString());
                academicLevelEdit.putExtra("academic_end_year", academicLevelListItem.getAcademicEndYear().toString());
                academicLevelEdit.putExtra("academic_year", academicLevelListItem.getAcademicYear().toString());
                academicLevelEdit.putExtra("academic_certificate", academicLevelListItem.getAcademicCertificate().toString());
                academicLevelEdit.putExtra("academic_ceritifcate_name", academicLevelListItem.getAcademicCertificateName().toString());
                academicLevelEdit.putExtra("academic_created", academicLevelListItem.getAcademicCreated().toString());
                academicLevelEdit.putExtra("academic_updated", academicLevelListItem.getAcademicDeleted().toString());

                academicLevelEdit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(academicLevelEdit);
            }

        });
    }



    @Override
    public int getItemCount() {
        return academicLevelListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewAcademicDegree;
        public TextView textViewAcademicInstitution;
        public TextView textViewAcademicDate;
        public LinearLayout academicLinearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewAcademicDegree = (TextView) itemView.findViewById(R.id.idacademic_degree);
            textViewAcademicInstitution = (TextView) itemView.findViewById(R.id.idacademic_institution);
            textViewAcademicDate = (TextView) itemView.findViewById(R.id.idacademic_date);
            academicLinearLayout = (LinearLayout) itemView.findViewById(R.id.idacademiclevels_LinearLayout);
        }
    }
}
