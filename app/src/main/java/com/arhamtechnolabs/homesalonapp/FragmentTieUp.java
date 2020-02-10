package com.arhamtechnolabs.homesalonapp;


import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.arhamtechnolabs.homesalonapp.DataModel.ListURL;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTieUp extends Fragment {

    private String[] country = { "Corporate it up", "Women entrepreneur", "Society Events"};
    private  String selected;
    private String HttpUrl = ListURL.TIE_UP_INSERT;
    private View rootView;
    private TextInputEditText tname, tcontact_no, tcompany_name, time_for_callback, comments;
    private String tname_holder, tcontact_n_holder, tcompany_name_holder, time_for_callback_holder, comments_holder;
    private Button submitTieUp;

    public FragmentTieUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_tie_up, container, false);

        getActivity().findViewById(R.id.addressTV).setVisibility(View.GONE);
        getActivity().findViewById(R.id.descTV).setVisibility(View.GONE);
        getActivity().findViewById(R.id.loc_img).setVisibility(View.GONE);

        TextView titleTV = getActivity().findViewById(R.id.titleTV);
        titleTV.setVisibility(View.VISIBLE);

        titleTV.setText("Tie Up");

        //get data from user VIEW ID
        tname = rootView.findViewById(R.id.tname);
        tcontact_no = rootView.findViewById(R.id.tcontact_no);
        tcompany_name = rootView.findViewById(R.id.tcompany_name);
        time_for_callback = rootView.findViewById(R.id.time_for_callback);
        comments = rootView.findViewById(R.id.comments);
        submitTieUp = rootView.findViewById(R.id.submitTieUp);
        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, country);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitTieUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tname_holder = tname.getText().toString().trim();
                tcontact_n_holder = tcontact_no.getText().toString().trim();
                tcompany_name_holder = tcompany_name.getText().toString().trim();
                time_for_callback_holder = time_for_callback.getText().toString().trim();
                comments_holder = comments.getText().toString().trim();

                Boolean check =  validationCheck();

                if (check){
                    sendData();
                }
                else{
                    System.out.println("11111");
                }

            }
        });


        return rootView;
    }

    private void sendData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String s = jsonObject.getString("success");
                    String m = jsonObject.getString("message");
                    System.out.println("If Message :" + m);

                    if (s.equals("1")){

                        ViewGroup viewGroup = rootView.findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.my_dialog, viewGroup, false);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setView(dialogView);
                        final AlertDialog alertDialog = builder.create();

                        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                        TextView message = dialogView.findViewById(R.id.message);
                        message.setText("Your Tie Up Request has been Submitted.");
                        alertDialog.show();
                        buttonOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                    }else{
                        Toast.makeText(getActivity(), "Error Occured!!!", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("type", selected);
                params.put("name", tname_holder);
                params.put("contact_no", tcontact_n_holder);
                params.put("company_name", tcompany_name_holder);
                params.put("time_for_callback", time_for_callback_holder);
                params.put("comments", comments_holder);


                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);


    }

    private Boolean validationCheck() {

        if (    tname.getText().toString().equals("") ||
                tcontact_no.getText().toString().equals("") ||
                tcompany_name.getText().toString().equals("") ||
                time_for_callback.getText().toString().equals("") ||
                comments.getText().toString().equals("")

        ){

            if ( tname.getText().toString().isEmpty()){
                tname.setError("Name is Required");
            }
            if ( tcontact_no.getText().toString().isEmpty()){
                tcontact_no.setError("Email is Required");
            }
            if ( tcompany_name.getText().toString().isEmpty()){
                tcompany_name.setError("Phone Number is Required");
            }
            if ( time_for_callback.getText().toString().isEmpty()){
                time_for_callback.setError("City is Required");
            }
            if ( comments.getText().toString().isEmpty()){
                comments.setError("State is Required");
            }


            System.out.println("TRUE VALIDATION");

            return false;
        }else{
            return true;
        }
    }


}
