package com.arhamtechnolabs.homesalonapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.arhamtechnolabs.homesalonapp.DataModel.SharedPreferenceHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class WalletFragment extends Fragment {


    public WalletFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =  inflater.inflate(R.layout.fragment_wallet, container, false);


        getActivity().findViewById(R.id.addressTV).setVisibility(View.GONE);
        getActivity().findViewById(R.id.descTV).setVisibility(View.GONE);
        getActivity().findViewById(R.id.loc_img).setVisibility(View.GONE);
        TextView titleTV = getActivity().findViewById(R.id.titleTV);
        titleTV.setVisibility(View.VISIBLE);

        EditText availBalance = view.findViewById(R.id.availBalance);
        SharedPreferenceHelper helper = new SharedPreferenceHelper(getContext());
        String wallet = String.valueOf(helper.getWalletBalance());
        availBalance.setText(Html.fromHtml("&#x20B9;")+ "  " + wallet);

        titleTV.setText("Home Salon Wallet");
        return view;

    }

}
