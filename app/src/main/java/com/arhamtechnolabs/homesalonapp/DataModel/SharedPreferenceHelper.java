package com.arhamtechnolabs.homesalonapp.DataModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.List;

public class SharedPreferenceHelper {

    Context context;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String MOBILE = "mobileKey";
    public static final String USER_ID = "userid";
    public static final String EMAIL = "useremail";
    public static final String WALLET_KEY = "walletBalance";
    public static final String USERNAME = "username";
    public static final String REFER_CODE = "refeercode";


    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    public SharedPreferenceHelper(Context context) {
        this.context = context;
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, 0);

    }

    public boolean checkExistanceOfUser() {
        String s = sharedpreferences.getString(EMAIL, "");

        Log.i("CheckExistanceShaE", s);
        //Log.i("CheckExistanceSDTE", DataTransferHelper.USER_MAIL);
        if (!s.equals("")) return true;

        return false;


    }
    public String getMobile() {
        return sharedpreferences.getString(MOBILE, "");
    }

    public void setMobileNumber(String mobileNumber) {
        editor = sharedpreferences.edit();
        editor.putString(MOBILE, mobileNumber);
        editor.commit();
        Log.i("SharedPreference", "Mobile Entered in pref");

    }
    public void  setEmail(String email) {
        editor = sharedpreferences.edit();
        editor.putString(EMAIL, email);
        editor.commit();
        Log.i("SharedPreference", "Email Entered in pref");
    }

    public void  setUsername(String username) {
        editor = sharedpreferences.edit();
        editor.putString(USERNAME, username);
        editor.commit();
        Log.i("SharedPreference", "username Entered in pref");
    }
    public int getWalletBalance() {

        String s = sharedpreferences.getString(WALLET_KEY, "");
        int n = Integer.parseInt(s);

        return n;

    }
    public String getUserName() {
        return sharedpreferences.getString(USERNAME, "");
    }
    public String getUserId() {
        return sharedpreferences.getString(USER_ID, "");
    }
    public String getEmail() {
        return sharedpreferences.getString(EMAIL, "");
    }
    public String getReferCode() {
        return sharedpreferences.getString(REFER_CODE, "");
    }
}
