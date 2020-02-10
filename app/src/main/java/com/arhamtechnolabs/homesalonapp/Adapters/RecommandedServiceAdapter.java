package com.arhamtechnolabs.homesalonapp.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arhamtechnolabs.homesalonapp.DataModel.CartData;
import com.arhamtechnolabs.homesalonapp.DataModel.DataHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.SubSubCategory;
import com.arhamtechnolabs.homesalonapp.MainActivity;
import com.arhamtechnolabs.homesalonapp.R;
import com.arhamtechnolabs.homesalonapp.SubCategoryAndPackagesActivity;
import com.arhamtechnolabs.homesalonapp.ui.home.HomeFragment;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecommandedServiceAdapter extends RecyclerView.Adapter<RecommandedServiceAdapter.ViewHolder>  {
    CartData cart;

    Context context;
    List<SubSubCategory> subSubCategories = new ArrayList<>();

    public RecommandedServiceAdapter(Context context, List<SubSubCategory> subSubCategories) {
        this.context = context;
        this.subSubCategories = subSubCategories;
    }

    @NonNull
    @Override
    public RecommandedServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View mView = LayoutInflater.from(context).inflate(R.layout.row_file, parent, false);
        return new ViewHolder(mView);
    }
    private TextView[] textViews;

    @Override
    public void onBindViewHolder(@NonNull final RecommandedServiceAdapter.ViewHolder holder, int position) {
        final SubSubCategory subSubCategory = subSubCategories.get(position);


        Glide.with(context).load(subSubCategory.getSubsubcategory_image())
                .into(holder.subsubcatimg);

        holder.subsubcatname.setText(  subSubCategory.getSubSubCat_Name());
        holder.subsubcatprice.setText(" Rs. " + subSubCategory.getSubSubCat_price() + "/-");
        holder.time_rec.setText(" (" + subSubCategory.getSubSubCat_timeInMin() + ") ");


        String[] features = subSubCategory.getSubSubCat_features().split(",");
        System.out.println(features[0]);
        textViews = new TextView[4];
        for (int i=0;i<2;i++){
            textViews[i]=new TextView(context);
            textViews[i].setText(Html.fromHtml("&#x2022;") +"  " + features[i]);
            holder.subsubcatdesc.addView(textViews[i]);
            textViews[i].setPadding(25,15,15,15);
            textViews[i].setTextSize(13);
            textViews[i].setGravity(View.TEXT_ALIGNMENT_CENTER);
            textViews[i].setCompoundDrawablePadding(20);
        }

        holder.addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.addcart.setVisibility(View.GONE);
                MainActivity activity = (MainActivity) context;
                cart = new CartData();
                cart.setId(Integer.parseInt(subSubCategory.getSubSubCat_ID()));
                cart.setCategoryName(subSubCategory.getSubSubCat_Name());
                cart.setAmount(subSubCategory.getSubSubCat_price());
                cart.setFeatures(subSubCategory.getSubSubCat_features());
                cart.setImg(subSubCategory.getSubsubcategory_image());
                cart.setAmountOfProducts(Integer.parseInt(subSubCategory.getSubSubCatProductCost()));
                HomeFragment fragment = new HomeFragment();
                fragment.updateCart();


                String[] min = subSubCategory.getSubSubCat_timeInMin().split(" ");
                cart.setTimeRequired(Integer.valueOf(min[0]));

                /*Settign number of product default to one*/
                cart.setNumberOfProducts(1);
                cart.setNumberOfTime(1);
                DataHelper dataHelper = new DataHelper(context);
                dataHelper.insertData(cart);
                List<CartData> carts = dataHelper.fetchRequest();

                for (int i=0;i<carts.size();i++){
                    System.out.println("carts : " + carts.get(i).toString());
                }
                Toast.makeText(context, "Item added to cart", Toast.LENGTH_LONG).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return subSubCategories.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder  {

        ImageView subsubcatimg;
        TextView subsubcatname, subsubcatprice,time_rec;
        LinearLayout subsubcatdesc;
        Button addcart;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            subsubcatimg = itemView.findViewById(R.id.subsubcatimg);
            subsubcatname = itemView.findViewById(R.id.subsubcatname);
            subsubcatdesc = itemView.findViewById(R.id.subsubcatdesc);
            subsubcatprice = itemView.findViewById(R.id.subsubcatprice);
            addcart = itemView.findViewById(R.id.addcart);
            time_rec = itemView.findViewById(R.id.time_rec);

        }
    }
}
