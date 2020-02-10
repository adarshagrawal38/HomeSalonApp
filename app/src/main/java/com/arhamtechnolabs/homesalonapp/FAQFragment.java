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
public class FAQFragment extends Fragment {
    PDFView pdfView;


    public FAQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_faq, container, false);

        getActivity().findViewById(R.id.addressTV).setVisibility(View.GONE);
        getActivity().findViewById(R.id.descTV).setVisibility(View.GONE);
        getActivity().findViewById(R.id.loc_img).setVisibility(View.GONE);
        TextView titleTV = getActivity().findViewById(R.id.titleTV);
        titleTV.setVisibility(View.VISIBLE);

        titleTV.setText("FAQs");

        pdfView = rootView.findViewById(R.id.pdfView);

        pdfView.fromAsset("test.pdf")
                .enableSwipe(true)
                .swipeHorizontal(false)
                .load();

        return rootView;
    }


}
