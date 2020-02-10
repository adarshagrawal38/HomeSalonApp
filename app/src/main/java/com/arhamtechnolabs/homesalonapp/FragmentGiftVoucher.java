package com.arhamtechnolabs.homesalonapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arhamtechnolabs.homesalonapp.Adapters.GiftVoucherAdapter;
import com.arhamtechnolabs.homesalonapp.DataModel.GiftVoucherMaster;
import com.arhamtechnolabs.homesalonapp.DataModel.ListURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentGiftVoucher extends Fragment {

    View rootView;
    String HttpURL = ListURL.VIEW_GIFT_VOUCHERS;
    RecyclerView recyclerView ;
    RecyclerView.Adapter adapter;
    List<GiftVoucherMaster> listItems;


    public FragmentGiftVoucher() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_fragment_gift_voucher, container, false);

        getActivity().findViewById(R.id.addressTV).setVisibility(View.GONE);
        getActivity().findViewById(R.id.descTV).setVisibility(View.GONE);
        getActivity().findViewById(R.id.loc_img).setVisibility(View.GONE);
        TextView titleTV = getActivity().findViewById(R.id.titleTV);
        titleTV.setVisibility(View.VISIBLE);

        titleTV.setText("Gift Vouchers");

        recyclerView = rootView.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listItems=new ArrayList<>();

        loadData();

        return rootView;
    }

    private void loadData() {

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

                        GiftVoucherMaster item = new GiftVoucherMaster(
                                jsonObject.getString("voucher_img"),
                                jsonObject.getString("voucher_price"),
                                jsonObject.getString("description"),
                                jsonObject.getString("note")
                        );
                        listItems.add(item);
                    }

                    adapter = new GiftVoucherAdapter(getActivity(),listItems);
                    recyclerView.setAdapter(adapter);

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
