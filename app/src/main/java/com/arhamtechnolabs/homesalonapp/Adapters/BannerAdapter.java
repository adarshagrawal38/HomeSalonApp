package com.arhamtechnolabs.homesalonapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arhamtechnolabs.homesalonapp.DataModel.BannerMaster;
import com.arhamtechnolabs.homesalonapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder>  {

    private Context context;
    private List<BannerMaster> listItems;

    public BannerAdapter(Context context, List<BannerMaster> listItems) {
        this.context = context;
        this.listItems = listItems;
    }


    @NonNull
    @Override
    public BannerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View mView = LayoutInflater.from(context).inflate(R.layout.banner_row_file, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerAdapter.ViewHolder holder, int position) {
        BannerMaster listItem = listItems.get(position);

        Glide.with(context).load(listItem.getImg()).into(holder.bannerImage);
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView bannerImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bannerImage = itemView.findViewById(R.id.bannerImage);

        }
    }
}
