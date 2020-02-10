package com.arhamtechnolabs.homesalonapp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arhamtechnolabs.homesalonapp.Adapters.BookingAdapter;
import com.arhamtechnolabs.homesalonapp.Adapters.BookingPagerAdapter;
import com.arhamtechnolabs.homesalonapp.Adapters.MyPagerAdapter;
import com.arhamtechnolabs.homesalonapp.DataModel.BookingAppoinementModel;
import com.arhamtechnolabs.homesalonapp.DataModel.ListURL;
import com.arhamtechnolabs.homesalonapp.DataModel.SharedPreferenceHelper;
import com.arhamtechnolabs.homesalonapp.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    View rootView;
    String HttpURL = ListURL.VIEW_BOOKING_;
    RecyclerView recyclerViewAppointment ;

    ViewPager pager1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gallery, container, false);

        getActivity().findViewById(R.id.titleTV).setVisibility(View.GONE);

        pager1 = rootView.findViewById(R.id.pager1);

        getActivity().findViewById(R.id.addressTV).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.descTV).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.loc_img).setVisibility(View.VISIBLE);
        TextView titleTV = getActivity().findViewById(R.id.titleTV);
        titleTV.setVisibility(View.VISIBLE);

        titleTV.setText("Appointments");

        BookingPagerAdapter myPagerAdapter = new BookingPagerAdapter(getActivity().getSupportFragmentManager() , 0);
        pager1.setAdapter(myPagerAdapter);

//        recyclerViewAppointment = rootView.findViewById(R.id.recyclerViewAppointment);

        TabLayout tabLayout1 = rootView.findViewById(R.id.tablayout1);
        tabLayout1.setupWithViewPager(pager1);



        return rootView;
    }




}