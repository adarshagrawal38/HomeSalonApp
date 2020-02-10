package com.arhamtechnolabs.homesalonapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class FragmentFranchise extends Fragment {


    private TextInputEditText name, email, phoneNo, city, state, age, occupation, workExperience, query;
    private String name_holder, email_holder, phoneNo_holder, city_holder, state_holder, age_holder, occupation_holder, workExperience_holder, query_holder, ownerSpace_holder;
    private RadioGroup radioGroup;
    private RadioButton ownerSpace;
    private String HttpUrl = ListURL.INSERT_FRANCHISE;
    View rootView;

    public FragmentFranchise() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_fragment_franchise, container, false);

        getActivity().findViewById(R.id.addressTV).setVisibility(View.GONE);
        getActivity().findViewById(R.id.descTV).setVisibility(View.GONE);
        getActivity().findViewById(R.id.loc_img).setVisibility(View.GONE);

        TextView titleTV = getActivity().findViewById(R.id.titleTV);

        //get data from user VIEW ID
        name = rootView.findViewById(R.id.name);
        email = rootView.findViewById(R.id.email);
        phoneNo = rootView.findViewById(R.id.phoneNo);
        city = rootView.findViewById(R.id.city);
        state = rootView.findViewById(R.id.state);
        age = rootView.findViewById(R.id.age);
        occupation = rootView.findViewById(R.id.occupation);
        workExperience = rootView.findViewById(R.id.workExperience);
        query = rootView.findViewById(R.id.query);

        radioGroup = rootView.findViewById(R.id.radioGroup);
        radioGroup.clearCheck();


        titleTV.setVisibility(View.VISIBLE);

        titleTV.setText("Franchise");

        Button submitFranchise;

        submitFranchise = rootView.findViewById(R.id.submitFranchise);
        submitFranchise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name_holder = name.getText().toString().trim();
                email_holder = email.getText().toString().trim();
                phoneNo_holder = phoneNo.getText().toString().trim();
                city_holder = city.getText().toString().trim();
                state_holder = state.getText().toString().trim();
                age_holder = age.getText().toString().trim();
                occupation_holder = occupation.getText().toString().trim();
                workExperience_holder = workExperience.getText().toString().trim();
                query_holder = query.getText().toString().trim();

//                int selectedId = radioGroup.getCheckedRadioButtonId();
//                ownerSpace =  rootView.findViewById(selectedId);
//                ownerSpace_holder = (String) ownerSpace.getText();

//                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                        RadioButton rb = (RadioButton) radioGroup.findViewById(i);
//
//                        System.out.println(rb.getText());
//                    }
//                });

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

    private Boolean validationCheck() {

        if (    name.getText().toString().equals("") ||
                email.getText().toString().equals("") ||
                phoneNo.getText().toString().equals("") ||
                city.getText().toString().equals("") ||
                state.getText().toString().equals("") ||
                age.getText().toString().equals("") ||
                occupation.getText().toString().equals("") ||
                workExperience.getText().toString().equals("") ||
                query.getText().toString().equals("")
        ){

            if ( name.getText().toString().isEmpty()){
                name.setError("Name is Required");
            }
            if ( email.getText().toString().isEmpty()){
                email.setError("Email is Required");
            }
            if ( phoneNo.getText().toString().isEmpty()){
                phoneNo.setError("Phone Number is Required");
            }
            if ( city.getText().toString().isEmpty()){
                city.setError("City is Required");
            }
            if ( state.getText().toString().isEmpty()){
                state.setError("State is Required");
            }
            if ( age.getText().toString().isEmpty()){
                age.setError("Age is Required");
            }
            if ( occupation.getText().toString().isEmpty()){
                occupation.setError("occupation is Required");
            }
            if ( workExperience.getText().toString().isEmpty()){
                workExperience.setError("Work Experience is Required");
            }
            if ( query.getText().toString().isEmpty()){
                query.setError("Query is Required");
            }

            System.out.println("TRUE VALIDATION");

            return false;
        }else{
            return true;
        }

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
                        message.setText("Your Query has been Submitted.");
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

                params.put("full_name", name_holder);
                params.put("email", email_holder);
                params.put("contact_no", phoneNo_holder);
                params.put("city", city_holder);
                params.put("state", state_holder);
                params.put("age", age_holder);
                params.put("occupation", occupation_holder);
                params.put("work_experience", workExperience_holder);
                params.put("owner_space", "yes");
                params.put("query", query_holder);

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

}
