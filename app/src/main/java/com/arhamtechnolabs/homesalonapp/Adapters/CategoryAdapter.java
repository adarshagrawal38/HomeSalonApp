package com.arhamtechnolabs.homesalonapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arhamtechnolabs.homesalonapp.DataModel.Category;
import com.arhamtechnolabs.homesalonapp.DataModel.ListURL;
import com.arhamtechnolabs.homesalonapp.DataModel.SubCategory;
import com.arhamtechnolabs.homesalonapp.R;
import com.arhamtechnolabs.homesalonapp.SubCategoryAndPackagesActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>  {

    Context context;
    List<Category> listItems;
    String SUB_CAT_URL = ListURL.VIEW_SUBCATEGORY;
    List<SubCategory> subCategoryList = new ArrayList<>();

    String subCategoryNameString, subCategoryIDString;

    public CategoryAdapter(Context context, List<Category> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View mView = LayoutInflater.from(context).inflate(R.layout.category_row_file, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Category listItem = listItems.get(position);

        Glide.with(context).load(listItem.getCat_img()).into(holder.catImage);
        holder.catName.setText(listItem.getCat_name().toString());
//        holder.rate_list_card_price.setText(listItem.getCat_ratePerMin().toString());

        final String id = listItem.getCatID();

        holder.layoutCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, SubCategoryAndPackagesActivity.class);
                intent.putExtra("id", id);
                System.out.println("newid : " + id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView catImage;
        TextView catName;
        LinearLayout layoutCat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            catImage = itemView.findViewById(R.id.catImage);
            catName = itemView.findViewById(R.id.catName);
            layoutCat = itemView.findViewById(R.id.layoutCat);

        }
    }
}
