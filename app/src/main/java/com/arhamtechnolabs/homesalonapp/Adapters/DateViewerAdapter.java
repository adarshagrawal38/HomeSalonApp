package com.arhamtechnolabs.homesalonapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arhamtechnolabs.homesalonapp.Booking;
import com.arhamtechnolabs.homesalonapp.DataModel.DataHelper;
import com.arhamtechnolabs.homesalonapp.DateGenerator;
import com.arhamtechnolabs.homesalonapp.DateHelper;
import com.arhamtechnolabs.homesalonapp.R;

import java.util.ArrayList;
import java.util.List;

public class DateViewerAdapter extends RecyclerView.Adapter<DateViewerAdapter.ViewHolder> {

    Context context;
    List<DateHelper> list;

    public DateViewerAdapter(Context context, List<DateHelper> deteList) {
        this.context = context;
        list = deteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View mView = LayoutInflater.from(context).inflate(R.layout.date_selector_row_layout, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final DateHelper dateHelper = list.get(position);

        holder.date.setText(dateHelper.getDayOfMonth());
        holder.day.setText(dateHelper.getDayOfWeek());
        holder.month.setText(dateHelper.getMonthName());

        int n = dateHelper.getColor();
        if (n==1) {
            holder.day.setTextColor(Color.WHITE);
            holder.ll.setBackgroundColor(Color.RED);
        }

//        holder.ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                list.get(position).setColor(1);
//                Booking booking = (Booking) context;
////                booking.updateColor(position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, date, month;
        LinearLayout ll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.day);
            date = itemView.findViewById(R.id.date);
            month = itemView.findViewById(R.id.month);
            ll = itemView.findViewById(R.id.ll);
        }
    }
}
