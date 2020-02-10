package com.arhamtechnolabs.homesalonapp.DataModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserHelper {

    Context context;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String MOBILE = "mobileKey";
    SharedPreferences sharedpreferences;
    String mobileNumberHolder;
    List<User> listItems;

    public UserHelper(Context context) {
        this.context = context;
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, 0);
    }

    public void getUser() {

        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, 0);
        String mobilenumber = sharedpreferences.getString(MOBILE, "");
        String HttpURL = ListURL.VIEW_USER_BY_PHONE + mobilenumber;
        Log.i("GetUserUrl", HttpURL);
        listItems = new ArrayList<>();


        final StringRequest stringRequest = new StringRequest(Request.Method.GET, HttpURL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("getuser requested");
                try {
                    JSONObject object = new JSONObject(response);
                    System.out.println(response);
                    JSONArray jsonArray = object.getJSONArray("data");

                    System.out.println(jsonArray);

                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        System.out.println(jsonObject);

                        User item = new User(
                                jsonObject.getString("id"),
                                jsonObject.getString("name"),
                                "male",
                                jsonObject.getString("email"),
                                jsonObject.getString("phone_no"),
                                jsonObject.getString("user_waller"),
                                jsonObject.getString("refer_code")
                        );
                        listItems.add(item);
                        System.out.println("listitem updated");


                        String name = listItems.get(0).getName().toString();
                        String user_waller = listItems.get(0).getUser_waller().toString();
                        String email = listItems.get(0).getEmail().toString();
                        String id = listItems.get(0).getId().toString();
                        String refer_code = listItems.get(0).getRefer_code().toString();
                        System.out.println("sharedpreference item updated");

                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        DataTransferHelper.USER_MAIL = email;
                        editor.putString("username", name);
                        editor.putString("walletBalance", user_waller);
                        editor.putString("useremail", email);
                        editor.putString("userid", id);
                        editor.putString("refeercode", refer_code);
                        System.out.println("sharedpreference item commited");
                        //editor.apply();
                        editor.commit();

                        Log.i("USER_DATA", "FETCHED");

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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void setUserID() {


        SharedPreferenceHelper helper = new SharedPreferenceHelper(context);
        String mobile = helper.getMobile();

        String url = ListURL.GET_USER_ID_VIA_MOBILE + mobile;
        Log.i("UserIdFetchUrl", url);
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("get userid");
                try {
                    JSONObject object = new JSONObject(response);
                    System.out.println(response);
                    JSONArray jsonArray = object.getJSONArray("data");

                    System.out.println(jsonArray);

                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        System.out.println(jsonObject);

                        String s = jsonObject.getString("user_id");
                        System.out.println("USER_DATA " + s);
                        SharedPreferences.Editor editor = sharedpreferences.edit();



                        editor.putString("userid", s);

                        System.out.println("sharedpreference user_id commited");
                        //editor.apply();
                        editor.commit();

                        Log.i("USER_DATA", "Id_FETCHED");

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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);



    }
}
