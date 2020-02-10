package com.arhamtechnolabs.homesalonapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.arhamtechnolabs.homesalonapp.DataModel.ListURL;
import com.arhamtechnolabs.homesalonapp.DataModel.LoginDetails;
import com.arhamtechnolabs.homesalonapp.DataModel.SharedPreferenceHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileActivity2 extends AppCompatActivity {

    TextView editButton1;
    Button submitProfile;
    EditText edName,edMobile, edEmail, edReferCode;
    private String HttpUrl = ListURL.INSERT_PROFILE;
    String mobileNUmber ;
    String mobileNumberHolder;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String MOBILE = "mobileKey";
    SharedPreferences sharedpreferences;
    String usergettingviaMobile = ListURL.VIEW_PROFILE_WITH_MOB;

    private String HttpUrlLogin = ListURL.INSERT_LOGIN_DETAILS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity2);
        setTitle("Profile");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        edReferCode = findViewById(R.id.edReferCode);
        editButton1 = findViewById(R.id.editButton1);
        submitProfile = findViewById(R.id.submitProfile);
        edName = findViewById(R.id.edName);
        edMobile = findViewById(R.id.edMobile);
        edEmail = findViewById(R.id.edEmail);
        edReferCode = findViewById(R.id.edReferCode);

        SharedPreferenceHelper helper = new SharedPreferenceHelper(getApplicationContext());
        String email= helper.getEmail();
        String mobile = helper.getMobile();
        String name =  helper.getUserName();

        edEmail.setText(email);
        edName.setText(name);
        edMobile.setText(mobile);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        mobileNumberHolder = sharedpreferences.getString(MOBILE, "");

        edMobile.setText(mobileNumberHolder);

        if (!mobileNumberHolder.equals("")){
            getUserDetails();
            //edReferCode.setVisibility(View.GONE);
        }


        editButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitProfile.setVisibility(View.VISIBLE);
                edName.setEnabled(true);
                edEmail.setEnabled(true);

            }
        });
        Log.i("check status1: ", String.valueOf(checkStatus()));

        submitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    editUserAPICall();
                Toast.makeText(ProfileActivity2.this, "Your profile updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    String referalURL = ListURL.UPDATE_WALLET_BALANCE;
    private void updateWalletBalance() {
        String referalCode = edReferCode.getText().toString().trim();

        referalURL+=referalCode+"&phone_no="+mobileNUmber;

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

    private void getUserDetails() {
        usergettingviaMobile = usergettingviaMobile + mobileNumberHolder;
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, usergettingviaMobile,new Response.Listener<String>() {
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

                        User item = new User(
                                jsonObject.getString("user_id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("gender"),
                                jsonObject.getString("email"),
                                jsonObject.getString("phone_no"),
                                jsonObject.getString("user_waller"),
                                jsonObject.getString("refer_code")
                        );

                        edName.setText(item.getName());
                        edEmail.setText(item.getEmail());
                        edName.setText(item.getName());
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

    }


    String editUserURL = ListURL.EDIT_USER_DETAILS;

    private void editUserAPICall() {


        editUserURL = editUserURL + mobileNumberHolder + "&name=" + edName.getText().toString().trim() + "&email=" + edEmail.getText().toString().trim();

        editUserURL = editUserURL.replace(" ", "%20");
        Log.i("EditURL", editUserURL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, editUserURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String s = jsonObject.getString("success");
                    String me = jsonObject.getString("message");
                    System.out.println("If Message :" + me);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    List<LoginDetails> loginDetails = new ArrayList<>();

    private boolean checkStatus(){
        System.out.println("checkStatus");

        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String usernameHolder = sharedpreferences.getString("username", "");
        if (usernameHolder.equals("")){
            return true;
        }
        return false;
    }

    private void SendLoginDetailsData() {
        System.out.println("check status sending login details");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String s = jsonObject.getString("success");
                    String me = jsonObject.getString("message");
                    System.out.println("If Message :" + me);
//                    if (s.equals("1")){
//                        Toast.makeText(ProfileActivity.this, "OTP VERIFY Successful", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Toast.makeText(ProfileActivity.this, "Error Occured!!!", Toast.LENGTH_LONG).show();
//                    }
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

                params.put("phone_no", mobileNumberHolder);
                params.put("status", "1");
                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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
                        Toast.makeText(ProfileActivity2.this, "Profile Added Successful", Toast.LENGTH_SHORT).show();
                        updateWalletBalance();
                        Intent i = new Intent(ProfileActivity2.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    }else{
                        Toast.makeText(ProfileActivity2.this, "Error Occured!!!", Toast.LENGTH_LONG).show();
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
                params.put("phone_no", mobileNumberHolder);
                params.put("email", edEmail.getText().toString().trim());

                return params;
            }
        };

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
    public void onBackPressed() {
        super.onBackPressed();
        //((SubCategoryAndPackagesActivity) getApplicationContext()).method();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}


