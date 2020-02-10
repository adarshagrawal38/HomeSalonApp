package com.arhamtechnolabs.homesalonapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arhamtechnolabs.homesalonapp.DataModel.GiftVoucherMaster;
import com.arhamtechnolabs.homesalonapp.GiftVoucherPerRow;
import com.arhamtechnolabs.homesalonapp.R;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GiftVoucherAdapter extends RecyclerView.Adapter<GiftVoucherAdapter.ViewHolder> {

    Context context;
    List<GiftVoucherMaster> listItems;

    public GiftVoucherAdapter(Context context, List<GiftVoucherMaster> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View mView = LayoutInflater.from(context).inflate(R.layout.row_gift_voucher, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftVoucherAdapter.ViewHolder holder, int position) {
        final GiftVoucherMaster listItem = listItems.get(position);

        Glide.with(context).load(listItem.getVoucher_img()).into(holder.landingImageView);

        holder.landingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, GiftVoucherPerRow.class);
                intent.putExtra("img_string", listItem.getVoucher_img());
                intent.putExtra("price", listItem.getVoucher_price());
                intent.putExtra("notes", listItem.getNote());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView landingImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            landingImageView = itemView.findViewById(R.id.landingImageView);

        }
    }
}
