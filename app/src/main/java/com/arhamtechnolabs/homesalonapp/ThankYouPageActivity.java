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

public class ThankYouPageActivity extends AppCompatActivity {


    private static final long SPLASH_TIME_OUT = 7000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main_with_skip_func);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {


                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                //i.putExtra("phone_no", mobileNumberHolder);
                startActivity(i);
                finish();


//


            }
        }, SPLASH_TIME_OUT);


    }
}
