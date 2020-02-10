package com.arhamtechnolabs.homesalonapp.Adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arhamtechnolabs.homesalonapp.ChckoutActivity;
import com.arhamtechnolabs.homesalonapp.DataModel.CartData;
import com.arhamtechnolabs.homesalonapp.DataModel.DataHelper;
import com.arhamtechnolabs.homesalonapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import pl.polak.clicknumberpicker.ClickNumberPickerListener;
import pl.polak.clicknumberpicker.ClickNumberPickerView;
import pl.polak.clicknumberpicker.PickerClickType;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.ViewHolder>  {

    Context context;
    List<CartData> listItems;

    public CheckoutAdapter(Context context, List<CartData> listItems) {
        this.context = context;
        this.listItems = listItems;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View mView = LayoutInflater.from(context).inflate(R.layout.checkout_row_file, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final CartData cartData = listItems.get(position);


        /*implementing picker*/

        /*Displat inital status number picker */
        holder.numberPickerDisplayTv.setText(String.valueOf(cartData.getNumberOfTime()));
        holder.numberPickerIncrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentNumber = Integer.parseInt(holder.numberPickerDisplayTv.getText().toString());
                currentNumber++;
                updateDatabase(position, holder, currentNumber);
                holder.numberPickerDisplayTv.setText(String.valueOf(currentNumber));
            }
        });
        holder. numberPickerDecrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentNumber = Integer.parseInt(holder.numberPickerDisplayTv.getText().toString());
                int newNumber = currentNumber - 1;
                if (newNumber==0)newNumber=1;

                updateDatabase(position, holder, newNumber);
                holder.numberPickerDisplayTv.setText(String.valueOf(newNumber));
            }
        });


        holder.packageName.setText(cartData.getCategoryName());

        String fetures[] = cartData.getFeatures().split(",");
        String featureString = "";
        for (int i =0; i<fetures.length;i++) {

            featureString+=String.valueOf(i+1)+". " + fetures[i].trim() + "\n";

        }

        holder.description.setText(featureString);

        holder.servicePicker.setPickerValue(cartData.getNumberOfTime());
        holder.productCost.setPickerValue(cartData.getNumberOfProducts());
//        holder.serviceCostTextView.setText(Html.fromHtml("&#x20B9;") +cartData.getAmount());
        holder.productCostTextView.setText(Html.fromHtml("&#x20B9;") +String.valueOf(cartData.getAmountOfProducts()));

        float x = holder.servicePicker.getValue();
        float y = Float.parseFloat(cartData.getAmount());

        holder.finalAmount.setText("Rs. : " + cartData.getTotalAmount());

        holder.servicePicker.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {

                int n = (int) currentValue;
                int productValue = (int)holder.productCost.getValue();

                /*When service is going down below product product gont allow it*/
                /*if (productValue > n) {
                    holder.servicePicker.setPickerValue(productValue);
                    Log.i("ProductValue", String.valueOf(productValue));

                    Toast.makeText(context, "Service cant be lass then product", Toast.LENGTH_LONG).show();
                }else{
                    *//*Valid decrement*//*


                }*/

                listItems.get(position).setNumberOfTime(n);
                int v = listItems.get(position).getTotalAmount();
                DataHelper dataHelper = new DataHelper(context);
                dataHelper.updateNumberOfTimes(listItems.get(position).getId(), n);
                Log.i("FinalAmount", String.valueOf(v));
                holder.finalAmount.setText("Rs. " + v);
                float total =0;
                for (int i=0;i<listItems.size();i++){
                    total+= listItems.get(i).getTotalAmount();
                }
                ChckoutActivity activity = (ChckoutActivity) context;
                activity.method2(total);

            }
        });
        holder.productCost.setClickNumberPickerListener(new ClickNumberPickerListener() {
            @Override
            public void onValueChange(float previousValue, float currentValue, PickerClickType pickerClickType) {

                int n = (int) currentValue;
                //holder.productCost
                listItems.get(position).setNumberOfProducts(n);
                int v = listItems.get(position).getTotalAmount();
                DataHelper dataHelper = new DataHelper(context);
                dataHelper.updateNumberOfProduct(listItems.get(position).getId(), n);

                Log.i("FinalAmount", String.valueOf(v));
                holder.finalAmount.setText("Rs. " + v);
                float total =0;
                for (int i=0;i<listItems.size();i++){
                    total+= listItems.get(i).getTotalAmount();
                }
                ChckoutActivity activity = (ChckoutActivity) context;
                activity.method2(total);



            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Delete : ", String.valueOf(position));

                /*Delete data from database*/

                Log.i("Database ", "ItemDeletionRequest");
                DataHelper dataHelper = new DataHelper(context);
                dataHelper.deleteItem(listItems.get(position));
                listItems.remove(position);
                notifyDataSetChanged();

                float total =0;
                for (int i=0;i<listItems.size();i++){
                    total+= listItems.get(i).getTotalAmount();
                }
                ChckoutActivity activity = (ChckoutActivity) context;
                activity.method2(total);


            }
        });

        float total =0;
        for (int i=0;i<listItems.size();i++){
            total+= listItems.get(i).getTotalAmount();
        }
        ChckoutActivity activity = (ChckoutActivity) context;
        activity.method2(total);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView packageName,description, finalAmount, serviceCostTextView, productCostTextView;
        ClickNumberPickerView servicePicker,productCost;
        Button deleteBtn;

        TextView numberPickerDisplayTv;
        Button numberPickerDecrementBtn, numberPickerIncrementBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            packageName = itemView.findViewById(R.id.packageName);
            description = itemView.findViewById(R.id.description);
            finalAmount = itemView.findViewById(R.id.finalAmount);
            servicePicker = itemView.findViewById(R.id.servicePicker);
            productCost = itemView.findViewById(R.id.productCost);
            productCostTextView = itemView.findViewById(R.id.productCostTextView);

            numberPickerDisplayTv = itemView.findViewById(R.id.display);
            numberPickerDecrementBtn = itemView.findViewById(R.id.decrement);
            numberPickerIncrementBtn = itemView.findViewById(R.id.increment);

            deleteBtn = itemView.findViewById(R.id.deleteItemFromCart);
            servicePicker.setPickerValue(1);
            productCost.setPickerValue(1);

//            deleteBtn.setText(Html.fromHtml("<font color='#f46567'>" + "&#128465;" + "</font>"));
        }
    }

    public void updateDatabase(int position, ViewHolder holder, int value) {
        listItems.get(position).setNumberOfTime(value);
        int v = listItems.get(position).getTotalAmount();
        DataHelper dataHelper = new DataHelper(context);
        dataHelper.updateNumberOfTimes(listItems.get(position).getId(), value);
        Log.i("FinalAmount", String.valueOf(v));
        holder.finalAmount.setText("Rs. " + v);
        float total =0;
        for (int i=0;i<listItems.size();i++){
            total+= listItems.get(i).getTotalAmount();
        }
        ChckoutActivity activity = (ChckoutActivity) context;
        activity.method2(total);

    }
}
