package com.arhamtechnolabs.homesalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arhamtechnolabs.homesalonapp.DataModel.SharedPreferenceHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.UserHelper;

public class OtpScreen extends AppCompatActivity {


    Button submitOTP;
    EditText otp;
    String otpString;
    Intent getIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_otp_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        submitOTP = findViewById(R.id.submitOTP);
        otp = findViewById(R.id.otp);

        getIntent = getIntent();
        final String m = getIntent.getStringExtra("mobileno");
        final String o = getIntent.getStringExtra("otp");

        Log.i("OTP", o);
        submitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String otpString = otp.getText().toString();
                if (o.equals(otpString)){
                    UserHelper userHelper = new UserHelper(getApplicationContext());
                    SharedPreferenceHelper helper = new SharedPreferenceHelper(getApplicationContext());
                    helper.setMobileNumber(m);

                    //settign user detaisl in shared pref
                    userHelper.getUser();


                    if (helper.checkExistanceOfUser()) {

                        /*User exists
                        * Direct to main activity
                        * */
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {

                        /*User not exists*/
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }



                }else{
                    Toast.makeText(OtpScreen.this, "OTP Wrong!!!", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
