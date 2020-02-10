package com.arhamtechnolabs.homesalonapp.Adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arhamtechnolabs.homesalonapp.DataModel.BannerMaster;
import com.arhamtechnolabs.homesalonapp.DataModel.CartData;
import com.arhamtechnolabs.homesalonapp.DataModel.DataHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.SubSubCategory;
import com.arhamtechnolabs.homesalonapp.R;
import com.arhamtechnolabs.homesalonapp.SubCategoryAndPackagesActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import pl.polak.clicknumberpicker.ClickNumberPickerListener;
import pl.polak.clicknumberpicker.ClickNumberPickerView;
import pl.polak.clicknumberpicker.PickerClickType;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder>  {

    private Context context;
    List<SubSubCategory> subSubCategories = new ArrayList<>();
    CartData cart;

    private List<SubSubCategory> listItems;
    private  List<SubSubCategory> imutableListItems;

    public FilterAdapter(Context context, List<SubSubCategory> listItems) {
        this.context = context;
        subSubCategories= listItems;
        imutableListItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View mView = LayoutInflater.from(context).inflate(R.layout.filter_row_file, parent, false);
        return new ViewHolder(mView);
    }
    private TextView[] textViews;

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final SubSubCategory subSubCategory = subSubCategories.get(position);

        /*Displat inital status number picker */

        holder.numberPickerIncrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentNumber = Integer.parseInt(holder.numberPickerDisplayTv.getText().toString());
                currentNumber++;
                updateDatabase(position, holder, currentNumber, subSubCategory);
                holder.numberPickerDisplayTv.setText(String.valueOf(currentNumber));
                Toast.makeText(context, "Item added into cart", Toast.LENGTH_LONG).show();
            }
        });
        holder. numberPickerDecrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentNumber = Integer.parseInt(holder.numberPickerDisplayTv.getText().toString());
                int newNumber = currentNumber - 1;


                updateDatabase(position, holder, newNumber, subSubCategory);
                holder.numberPickerDisplayTv.setText(String.valueOf(newNumber));
            }
        });


        holder.SubSubCat_Name.setText(  subSubCategory.getSubSubCat_Name());
        holder.SubSubCat_price.setText(" " + subSubCategory.getSubSubCat_price() + "/-");

        holder.SubSubCat_timeInMin.setText(" " + subSubCategory.getSubSubCat_timeInMin());

        DataHelper dataHelper = new DataHelper(context);
        int numberOftimeItemPurchased = dataHelper.getNumberOfTime(Integer.valueOf(subSubCategory.getSubSubCat_ID()));

        if (numberOftimeItemPurchased > 0)  {
            LinearLayout.LayoutParams d = new LinearLayout.LayoutParams(
                    250,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );

            String dis = subSubCategory.getSubSubCat_discount();
            Log.i("PackageAdapter_disMain", String.valueOf(dis));
            char c[] = dis.toCharArray();
            String temp="";
            for (int i=0;i<c.length-1;i++) {
                temp+=c[i];
            }
            Log.i("PackageAdapter_temp", String.valueOf(temp));
            double dis12 = Double.parseDouble(temp);
            dis12/=100.0;
            Log.i("PackageAdapter_Dis", String.valueOf(dis12));
            double disPrice = Integer.parseInt(subSubCategory.getSubSubCat_price())*dis12 + Double.parseDouble(subSubCategory.getSubSubCat_price());
            int disInt = (int) disPrice;
            Log.i("PackageAdapter", String.valueOf(disInt));
            //holder.textViewDiscountedPrice.setText(String.valueOf(disInt));
            //TextView t= (TextView).findViewById(R.id.thousand));
            //for strking





            holder.addToCart.setVisibility(View.GONE);
            //holder.picker.setVisibility(View.VISIBLE);
            //holder.picker.setLayoutParams(d);
            holder.picker.setPickerValue(numberOftimeItemPurchased);
            holder.numberPickerLayout.setVisibility(View.VISIBLE);
            holder.numberPickerDisplayTv.setText(String.valueOf(numberOftimeItemPurchased));
        }

        Glide.with(context).load(subSubCategory.getSubsubcategory_image())
                .into(holder.subsubcategory_image);

        String[] features = subSubCategory.getSubSubCat_features().split(",");
        Set<String> strings = new HashSet<>();
        for (int i=0;i<features.length;i++) {
            strings.add(features[i]);
        }
        List<String> list = new ArrayList<>();
        list.addAll(strings);
        features = new String[list.size()];

        for (int i=0;i<list.size();i++) {
            features[i]=list.get(i);
        }

        System.out.println(features[0]);
        textViews = new TextView[4];
        holder.linearLayoutFeatures.removeAllViews();
        for (int i=0;i<2;i++){

            textViews[i]=new TextView(context);
            textViews[i].setText(Html.fromHtml("&#x2022;") +"  " + features[i]);
            holder.linearLayoutFeatures.addView(textViews[i]);
            textViews[i].setPadding(25,15,15,15);
            textViews[i].setTextSize(14);
            textViews[i].setGravity(View.TEXT_ALIGNMENT_CENTER);
            textViews[i].setCompoundDrawablePadding(20);
        }

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*SharedPreferences.Editor editor = sharedpreferences.edit();
                SharedPreferences shared = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);*/

//                SubCategoryAndPackagesActivity activity = (SubCategoryAndPackagesActivity) context;
                cart = new CartData();
                cart.setId(Integer.parseInt(subSubCategory.getSubSubCat_ID()));
                cart.setCategoryName(subSubCategory.getSubSubCat_Name());
                cart.setAmount(subSubCategory.getSubSubCat_price());
                cart.setFeatures(subSubCategory.getSubSubCat_features());
                cart.setImg(subSubCategory.getSubsubcategory_image());
                cart.setAmountOfProducts(Integer.parseInt(subSubCategory.getSubSubCatProductCost()));

                String[] min = subSubCategory.getSubSubCat_timeInMin().split(" ");
                cart.setTimeRequired(Integer.valueOf(min[0]));

                /*Settign number of product default to one*/
                cart.setNumberOfProducts(1);
                DataHelper dataHelper = new DataHelper(context);
                dataHelper.insertData(cart);
                List<CartData> carts = dataHelper.fetchRequest();

                for (int i=0;i<carts.size();i++){
                    System.out.println("carts : " + carts.get(i).toString());
                }
//                activity.method();
                LinearLayout.LayoutParams d = new LinearLayout.LayoutParams(
                        250,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );


                holder.numberPickerLayout.setVisibility(View.VISIBLE);
                holder.picker.setLayoutParams(d);
                //holder.picker.setPickerValue(1);
                holder.addToCart.setVisibility(View.GONE);
                holder.numberPickerDisplayTv.setText(String.valueOf(1));

            }
        });
        /*Set listner over picker to perform necessary action*/
        holder.picker.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                int n = (int) currentValue;
//                SubCategoryAndPackagesActivity activity = (SubCategoryAndPackagesActivity) context;
                /*id of item which is getting updated*/
                int itemUpdateId = Integer.parseInt(subSubCategory.getSubSubCat_ID());
                DataHelper dataHelper = new DataHelper(context);
                dataHelper.updateNumberOfTimes(itemUpdateId, n);
                dataHelper.updateNumberOfProduct(itemUpdateId, n);

                if (n==0) {
                    LinearLayout.LayoutParams d = new LinearLayout.LayoutParams(
                            250,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );

                    //Item is removed from the cart
                    dataHelper.deleteItem(itemUpdateId);
                    holder.picker.setVisibility(View.GONE);
                    holder.picker.setLayoutParams(d);
                    holder.addToCart.setVisibility(View.VISIBLE);

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return subSubCategories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {


        ImageView subsubcategory_image;
        TextView SubSubCat_price, SubSubCat_timeInMin, SubSubCat_Desc,SubSubCat_Name;
        LinearLayout linearLayoutFeatures;
//        LinearLayout linearLayoutSubSubCat;
        TextView addToCart;
        ClickNumberPickerView picker;

        TextView numberPickerDisplayTv;
        Button numberPickerDecrementBtn, numberPickerIncrementBtn;
        View numberPickerLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SubSubCat_price = itemView.findViewById(R.id.SubSubCat_price);
            subsubcategory_image = itemView.findViewById(R.id.subsubcategory_image);
            SubSubCat_timeInMin = itemView.findViewById(R.id.SubSubCat_timeInMin);
            SubSubCat_Name = itemView.findViewById(R.id.SubSubCat_Name);
            linearLayoutFeatures = itemView.findViewById(R.id.linearLayoutFeatures);
            addToCart = itemView.findViewById(R.id.addToCart);
            picker = itemView.findViewById(R.id.sub_picker);

            numberPickerDisplayTv = itemView.findViewById(R.id.display);
            numberPickerDecrementBtn = itemView.findViewById(R.id.decrement);
            numberPickerIncrementBtn = itemView.findViewById(R.id.increment);


            numberPickerLayout = itemView.findViewById(R.id.numberPcikerLayout);

        }
    }

    public void updateDatabase(int position, ViewHolder holder, int value, SubSubCategory subSubCategory) {
        //SubCategoryAndPackagesActivity activity = (SubCategoryAndPackagesActivity) context;
        /*id of item which is getting updated*/
        int itemUpdateId = Integer.parseInt(subSubCategory.getSubSubCat_ID());
        DataHelper dataHelper = new DataHelper(context);
        dataHelper.updateNumberOfTimes(itemUpdateId, value);
        dataHelper.updateNumberOfProduct(itemUpdateId, value);

        if (value==0) {
            LinearLayout.LayoutParams d = new LinearLayout.LayoutParams(
                    150,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );

            //Item is removed from the cart
            dataHelper.deleteItem(itemUpdateId);
            holder.numberPickerLayout.setVisibility(View.GONE);
            holder.picker.setLayoutParams(d);
            holder.addToCart.setVisibility(View.VISIBLE);

        }

        //activity.method();

    }
}
