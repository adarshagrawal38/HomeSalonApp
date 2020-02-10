package com.arhamtechnolabs.homesalonapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arhamtechnolabs.homesalonapp.ChckoutActivity;
import com.arhamtechnolabs.homesalonapp.DataModel.CartData;
import com.arhamtechnolabs.homesalonapp.DataModel.DataHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.SubSubCategory;
import com.arhamtechnolabs.homesalonapp.R;
import com.arhamtechnolabs.homesalonapp.SubCategoryAndPackagesActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import pl.polak.clicknumberpicker.ClickNumberPickerListener;
import pl.polak.clicknumberpicker.ClickNumberPickerView;
import pl.polak.clicknumberpicker.PickerClickType;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {

    Context context;
    List<SubSubCategory> subSubCategories = new ArrayList<>();
    CartData cart;
    SharedPreferences sharedpreferences;

    int counter =0;
    public static final String MyPREFERENCES = "CART" ;
    public static final String sub_sub_cat_TIME_IN_MIN = "time_in_min";
    public static final String Amount = "amountkey";
    public static final String sub_sub_counter = "counter";

    TextView checkout;

    public PackageAdapter(Context context, List<SubSubCategory> subSubCategories) {
        this.context = context;
        this.subSubCategories = subSubCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View mView = LayoutInflater.from(context).inflate(R.layout.package_card_layout, parent, false);
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
        holder.textViewDiscountedPrice.setText(String.valueOf(2000));
        holder.textViewDiscountedPrice.setPaintFlags(holder.textViewDiscountedPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

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
            holder.textViewDiscountedPrice.setText(String.valueOf(disInt));
            //TextView t= (TextView).findViewById(R.id.thousand));
            //for strking





            holder.addToCart.setVisibility(View.GONE);
            //holder.picker.setVisibility(View.VISIBLE);
            //holder.picker.setLayoutParams(d);
            holder.picker.setPickerValue(numberOftimeItemPurchased);
            holder.numberPickerLayout.setVisibility(View.VISIBLE);
            holder.numberPickerDisplayTv.setText(String.valueOf(numberOftimeItemPurchased));
        }

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        Glide.with(context).load(subSubCategory.getSubsubcategory_image())
                .into(holder.subsubcategory_image);

        final LinearLayout.LayoutParams dim = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        String[] features = subSubCategory.getSubSubCat_features().split(",");

        System.out.println(features[0]);
        textViews = new TextView[4];
        for (int i=0;i<features.length;i++){

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

                SubCategoryAndPackagesActivity activity = (SubCategoryAndPackagesActivity) context;
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
                activity.method();
                LinearLayout.LayoutParams d = new LinearLayout.LayoutParams(
                        150,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );

                /*Removing button and replacing it with number picker*/
                //holder.picker.setVisibility(View.VISIBLE);
                holder.numberPickerLayout.setVisibility(View.VISIBLE);
                holder.picker.setLayoutParams(d);
                holder.picker.setPickerValue(1);
                holder.addToCart.setVisibility(View.GONE);
                holder.numberPickerDisplayTv.setText(String.valueOf(1));

            }
        });
        /*Set listner over picker to perform necessary action*/
        holder.picker.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {
                int n = (int) currentValue;
                SubCategoryAndPackagesActivity activity = (SubCategoryAndPackagesActivity) context;
                /*id of item which is getting updated*/
                int itemUpdateId = Integer.parseInt(subSubCategory.getSubSubCat_ID());
                DataHelper dataHelper = new DataHelper(context);
                dataHelper.updateNumberOfTimes(itemUpdateId, n);
                dataHelper.updateNumberOfProduct(itemUpdateId, n);

                if (n==0) {
                    LinearLayout.LayoutParams d = new LinearLayout.LayoutParams(
                            150,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    );

                    //Item is removed from the cart
                    dataHelper.deleteItem(itemUpdateId);
                    holder.picker.setVisibility(View.GONE);
                    holder.picker.setLayoutParams(d);
                    holder.addToCart.setVisibility(View.VISIBLE);

                }

                activity.method();
            }
        });


        /*Item got clicked
        * Open pagevier
        * */
        holder.linearLayoutSubSubCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ItemClicked", "Detected");
            }
        });


    }



    @Override
    public int getItemCount() {
        return subSubCategories.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder  {

        ImageView subsubcategory_image;
        TextView SubSubCat_price, SubSubCat_timeInMin, SubSubCat_Desc,SubSubCat_Name, textViewDiscountedPrice;
        TextView addToCart;
        LinearLayout linearLayoutFeatures;
        ClickNumberPickerView picker;
        LinearLayout linearLayoutSubSubCat;
        View numberPickerLayout;

        TextView numberPickerDisplayTv;
        Button numberPickerDecrementBtn, numberPickerIncrementBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            SubSubCat_price = itemView.findViewById(R.id.SubSubCat_price);
            SubSubCat_timeInMin = itemView.findViewById(R.id.SubSubCat_timeInMin);
            SubSubCat_Name = itemView.findViewById(R.id.SubSubCat_Name);
            addToCart = itemView.findViewById(R.id.addToCart);
            subsubcategory_image = itemView.findViewById(R.id.subsubcategory_image);
            linearLayoutFeatures = itemView.findViewById(R.id.linearLayoutFeatures);
            picker = itemView.findViewById(R.id.sub_picker);

            numberPickerDisplayTv = itemView.findViewById(R.id.display);
            numberPickerDecrementBtn = itemView.findViewById(R.id.decrement);
            numberPickerIncrementBtn = itemView.findViewById(R.id.increment);


            numberPickerLayout = itemView.findViewById(R.id.numberPcikerLayout);

            linearLayoutSubSubCat = itemView.findViewById(R.id.linearLayoutSubSubCat);
            textViewDiscountedPrice = itemView.findViewById(R.id.disc_price);

        }
    }

    public void updateDatabase(int position, ViewHolder holder, int value, SubSubCategory subSubCategory) {
        SubCategoryAndPackagesActivity activity = (SubCategoryAndPackagesActivity) context;
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

        activity.method();

    }
}
