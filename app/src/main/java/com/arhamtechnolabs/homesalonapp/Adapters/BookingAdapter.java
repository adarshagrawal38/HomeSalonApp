package com.arhamtechnolabs.homesalonapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arhamtechnolabs.homesalonapp.DataModel.BookingAppoinementModel;
import com.arhamtechnolabs.homesalonapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    Context context;
    List<BookingAppoinementModel> listItems;

    String message;

    public BookingAdapter(Context context, List<BookingAppoinementModel> listItems,final String message) {
        this.context = context;
        this.listItems = listItems;
        this.message = message;
    }

    @NonNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View mView = LayoutInflater.from(context).inflate(R.layout.booking_appointment_row_file, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.ViewHolder holder, int position) {
        final BookingAppoinementModel bookingAppoinementModel = listItems.get(position);

        holder.serviceNameTv.setText(bookingAppoinementModel.getSubSubCat_ID());
        holder.bookingDate.setText("Date : " + bookingAppoinementModel.getDate());
        holder.bookingid.setText("Booking Id : " +bookingAppoinementModel.getBooking_id());
        holder.bookingTime.setText( bookingAppoinementModel.getBooking_time());
        holder.bookingAddress.setText(
                bookingAppoinementModel.getAddress());
        holder.bookingAmount.setText(bookingAppoinementModel.getAmount());
        holder.bookingmode.setText(bookingAppoinementModel.getPayment_mode());
        holder.orderStatusTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
        holder.shareLocationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Location : \n " + bookingAppoinementModel.getAddress());
                sendIntent.setPackage("com.whatsapp");
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView bookingDate, bookingTime, bookingAddress, bookingAmount, bookingmode, bookingid, serviceNameTv, orderStatusTextView, shareLocationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookingDate = itemView.findViewById(R.id.bookingDate);
            bookingTime = itemView.findViewById(R.id.bookingTime);
            bookingAddress = itemView.findViewById(R.id.bookingAddress);
            bookingAmount = itemView.findViewById(R.id.bookingAmount);
            bookingmode = itemView.findViewById(R.id.bookingmode);
            bookingid = itemView.findViewById(R.id.bookingid);
            serviceNameTv = itemView.findViewById(R.id.serviceNameTv);
            orderStatusTextView = itemView.findViewById(R.id.orderStatusTextView);
            shareLocationTextView = itemView.findViewById(R.id.shareLocationTextView);


        }
    }
}
