package com.arhamtechnolabs.homesalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.arhamtechnolabs.homesalonapp.DataModel.SharedPreferenceHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.UserHelper;

public class SplashScreen extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
//        sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                //Intent i = new Intent(SplashScreen.this, LoginActivity.class);

//                mobileNumberHolder = sharedpreferences.getString(MOBILE, "");
//
//                if (mobileNumberHolder.equals("")) {


                SharedPreferenceHelper helper = new SharedPreferenceHelper(getApplicationContext());
                UserHelper userHelper = new UserHelper(getApplicationContext());
                userHelper.getUser();
                userHelper.setUserID();
                String mobile = helper.getMobile();


                if (helper.checkExistanceOfUser()) {
                    Log.i("UserExsit", "sffvfv");
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    //i.putExtra("phone_no", mobileNumberHolder);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                    //i.putExtra("phone_no", mobileNumberHolder);
                    startActivity(i);
                    finish();
                }

//


            }
        }, SPLASH_TIME_OUT);
    }
}
