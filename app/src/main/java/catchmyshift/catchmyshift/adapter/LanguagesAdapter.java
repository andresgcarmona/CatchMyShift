package catchmyshift.catchmyshift.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import catchmyshift.catchmyshift.R;
import catchmyshift.catchmyshift.listitem.LanguagesListItem;

/**
 * Created by melli on 03/09/2017.
 */

public class LanguagesAdapter extends RecyclerView.Adapter<LanguagesAdapter.ViewHolder> {

    List<LanguagesListItem> languagesListItems;
    Context context;

    public LanguagesAdapter(List<LanguagesListItem> languagesListItems, Context context) {
        this.languagesListItems = languagesListItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_languages,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LanguagesListItem languagesListItem = languagesListItems.get(position);

        holder.textViewLanguage.setText(languagesListItem.getLanguage());
        holder.textViewLanguageLevel.setText(languagesListItem.getLanguageLevel());
        holder.textViewLanguageComments.setText(languagesListItem.getLanguageComments());
    }

    @Override
    public int getItemCount() {
        return languagesListItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewLanguage;
        public TextView textViewLanguageLevel;
        public TextView textViewLanguageComments;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewLanguage = (TextView) itemView.findViewById(R.id.idlanguage);
            textViewLanguageLevel = (TextView) itemView.findViewById(R.id.idlanguage_level);
            textViewLanguageComments = (TextView) itemView.findViewById(R.id.idlanguage_comments);
        }
    }
}
