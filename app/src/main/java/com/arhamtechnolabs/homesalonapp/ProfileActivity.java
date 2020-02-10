package com.arhamtechnolabs.homesalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arhamtechnolabs.homesalonapp.DataModel.ListURL;
import com.arhamtechnolabs.homesalonapp.DataModel.SharedPreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    TextView editButton;
    Button  submitProfile;
    EditText edName,edMobile, edEmail, edReferCode;
    SharedPreferenceHelper sharedPreferenceHelper;
    private String HttpUrl = ListURL.INSERT_PROFILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //REFERENCING
        editButton = findViewById(R.id.editButton);
        edName = findViewById(R.id.edName);
        edMobile = findViewById(R.id.edMobile);
        edEmail = findViewById(R.id.edEmail);
        edReferCode = findViewById(R.id.edReferCode);
        submitProfile = findViewById(R.id.submitProfile);

        sharedPreferenceHelper = new SharedPreferenceHelper(getApplicationContext());
        final String mobile = sharedPreferenceHelper.getMobile();
        String username = sharedPreferenceHelper.getUserName();
        String email = sharedPreferenceHelper.getEmail();

        edName.setText(username);
        edEmail.setText(email);

        if (mobile.equals("")) edMobile.setEnabled(true);
        edMobile.setText(mobile);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitProfile.setVisibility(View.VISIBLE);
                edName.setEnabled(true);
                edEmail.setEnabled(true);
            }
        });

        submitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Take data*/
                String nameString = edName.getText().toString().trim();
                String mobileString = edMobile.getText().toString().trim();
                String emailString = edEmail.getText().toString().trim();
                String referalString = edReferCode.getText().toString().trim();

                /*Check data */
                if (nameString.equals("") || mobileString.equals("") || emailString.equals("") || mobileString.length()!=10) {
                    if (mobileString.length() != 10) {
                        Toast.makeText(ProfileActivity.this, "Mobile Number should contain 10 number", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(ProfileActivity.this, "All feild Required", Toast.LENGTH_LONG).show();
                    }

                }else {


                    /*All data available*/
                    sendData();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void sendData() {


        System.out.println("Sending USER profile");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    System.out.println("Sending USER profile onResponse");

                    JSONObject jsonObject = new JSONObject(response);
                    String s = jsonObject.getString("success");
                    String m = jsonObject.getString("message");
                    System.out.println("If Message :" + m);

                    if (s.equals("1")){
                        System.out.println("User Profile :" + s);
                        /*Uppading wallet*/
                        updateWalletBalance();
                        String email = edName.getText().toString().trim();
                        String phone = edMobile.getText().toString().trim();
                        String name = edName.getText().toString().trim();
                        SharedPreferenceHelper helper = new SharedPreferenceHelper(getApplicationContext());
                        helper.setUsername(name);
                        helper.setEmail(email);
                        helper.setMobileNumber(phone);


                        Toast.makeText(ProfileActivity.this, "Profile Added Successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(ProfileActivity.this, "Error Occured!!!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", edName.getText().toString().trim());
                params.put("gender", "male");
                params.put("phone_no", edMobile.getText().toString().trim());
                params.put("email", edEmail.getText().toString().trim());
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }


    String referalURL = ListURL.UPDATE_WALLET_BALANCE;
    private void updateWalletBalance() {
        String referalCode = edReferCode.getText().toString().trim();
        String mobileString = edMobile.getText().toString().trim();

        if (!referalCode.equals("")){
            referalURL+=referalCode+"&phone_no="+mobileString;

            System.out.println("eferal Url"+ referalURL);
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, referalURL,new Response.Listener<String>() {
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
                        }

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

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

            System.out.println("Wallet update completed");
        }






    }




}
