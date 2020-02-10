package com.arhamtechnolabs.homesalonapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPrivacyPolicy extends Fragment {


    public FragmentPrivacyPolicy() {
        // Required empty public constructor
    }

    PDFView pdfView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragment_privacy_policy, container, false);

        getActivity().findViewById(R.id.addressTV).setVisibility(View.GONE);
        getActivity().findViewById(R.id.descTV).setVisibility(View.GONE);
        getActivity().findViewById(R.id.loc_img).setVisibility(View.GONE);
        TextView titleTV = getActivity().findViewById(R.id.titleTV);
        titleTV.setVisibility(View.VISIBLE);

        titleTV.setText("Privacy Policy");

        pdfView1 = rootView.findViewById(R.id.pdfView1);

        pdfView1.fromAsset("test.pdf")
                .enableSwipe(true)
                .swipeHorizontal(false)
                .load();

        return rootView;
    }

}
