package com.arhamtechnolabs.homesalonapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arhamtechnolabs.homesalonapp.DataModel.SharedPreferenceHelper;
import com.arhamtechnolabs.homesalonapp.R;


public class InviteCodeFragment extends Fragment {

    public InviteCodeFragment() {
        // Required empty public constructor
    }
    TextView tvInvite, tvsecondLine;
    String inviteString="Your friend earn <b> <font color=#cc0029>&#8377; 100 </font></b> using your <b> <font color=#cc0029>Referral Code</font></b>";

    String secondLine ="Your earn <b> <font color=#cc0029>&#8377; 150 </font></b> for every friend who completes his/her first booking";
    Button sendInvite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_invite_code, container, false);

        tvsecondLine = rootView.findViewById(R.id.tvsecondLine);
        tvInvite = rootView.findViewById(R.id.tvInviteText);
        tvInvite.setText(Html.fromHtml(inviteString));
        tvsecondLine.setText(Html.fromHtml(secondLine));
        sendInvite = rootView.findViewById(R.id.sendInvite);
        TextView textView = rootView.findViewById(R.id.tvText);

        SharedPreferenceHelper helper = new SharedPreferenceHelper(getContext());
        final String ref = helper.getReferCode();

        textView.setText("Share Your Referral Code \n " + ref);

        sendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Download Home Salon Application and get Exciting Rewards and Offers. Use my Referral code to get more 25. \n My Code:" + ref);
                sendIntent.setPackage("com.whatsapp");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        return rootView;
    }


}
