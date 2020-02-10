package com.arhamtechnolabs.homesalonapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arhamtechnolabs.homesalonapp.Adapters.BookingAdapter;
import com.arhamtechnolabs.homesalonapp.DataModel.BookingAppoinementModel;
import com.arhamtechnolabs.homesalonapp.DataModel.ListURL;
import com.arhamtechnolabs.homesalonapp.DataModel.SharedPreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OnGoingFragment extends Fragment {
    String HttpURL = ListURL.VIEW_BOOKING_;
    RecyclerView.Adapter adapter;
    List<BookingAppoinementModel> listItems;

    public OnGoingFragment() {
        // Required empty public constructor
    }

    View rootView;
    RecyclerView recyclerViewOnGoingAppointment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_on_going, container, false);
        recyclerViewOnGoingAppointment = rootView.findViewById(R.id.recyclerViewOnGoingAppointment);

        recyclerViewOnGoingAppointment.setHasFixedSize(true);
        recyclerViewOnGoingAppointment.setLayoutManager(new LinearLayoutManager(getActivity()));

        listItems=new ArrayList<>();

        loadData();

        return rootView;
    }

    private void loadData() {

        SharedPreferenceHelper helper = new SharedPreferenceHelper(getContext());
        String mobile = helper.getMobile();

        HttpURL = HttpURL + mobile;

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    System.out.println(response);
                    JSONArray jsonArray = object.getJSONArray("data");

                    System.out.println(jsonArray);

                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        System.out.println(jsonObject);

                        BookingAppoinementModel item = new BookingAppoinementModel(
                                jsonObject.getString("booking_id"),
                                jsonObject.getString("user_id"),
                                jsonObject.getString("date"),
                                jsonObject.getString("booking_time"),
                                jsonObject.getString("address"),
                                jsonObject.getString("SubSubCat_ID"),
                                jsonObject.getString("amount"),
                                jsonObject.getString("payment_mode")

                        );

                        String dataBaseDate = item.getDate();
                        //item.setDate(dataBaseDate);
                        Date currentDate = Calendar.getInstance().getTime();
                        Date bookingDate = DateGenerator.convertStringToDate(dataBaseDate);

                        if (currentDate.compareTo(bookingDate)<=0) {
                            Log.i("BookingDate", "After");
                            listItems.add(item);

                        }

                    }



                    adapter = new BookingAdapter(getActivity(),listItems, "Your order is pending");
                    recyclerViewOnGoingAppointment.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        } );

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

}
