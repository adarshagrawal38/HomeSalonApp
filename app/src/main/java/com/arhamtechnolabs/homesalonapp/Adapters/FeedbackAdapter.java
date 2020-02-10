package com.arhamtechnolabs.homesalonapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arhamtechnolabs.homesalonapp.DataModel.FeedbackMaster;
import com.arhamtechnolabs.homesalonapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FeedbackAdapter  extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder>  {

    private Context context;
    private List<FeedbackMaster> listItems;

    public FeedbackAdapter(Context context, List<FeedbackMaster> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View mView = LayoutInflater.from(context).inflate(R.layout.feedback_row_file, parent, false);
        return new ViewHolder(mView);

    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.ViewHolder holder, int position) {

        final FeedbackMaster listItem = listItems.get(position);

        holder.testimonial_card_view_client_name.setText(listItem.getName());
        holder.testimonial_card_view_desc.setText(listItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView testimonial_card_view_client_name, testimonial_card_view_desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            testimonial_card_view_client_name = itemView.findViewById(R.id.testimonial_card_view_client_name);
            testimonial_card_view_desc = itemView.findViewById(R.id.testimonial_card_view_desc);
        }
    }
}
