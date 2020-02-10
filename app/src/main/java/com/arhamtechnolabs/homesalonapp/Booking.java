package com.arhamtechnolabs.homesalonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arhamtechnolabs.homesalonapp.Adapters.CheckoutAdapter;
import com.arhamtechnolabs.homesalonapp.Adapters.DateViewerAdapter;
import com.arhamtechnolabs.homesalonapp.DataModel.DataTransferHelper;

import java.util.Date;
import java.util.List;

public class Booking extends AppCompatActivity implements View.OnClickListener {

    Button one, two, three, four, five, six, seven, eight, nine, locationChange;
    TextView locationAddress, proceed  ;
    RecyclerView dateViewer;
    Button button1,button2,button3,button4,button5,button6,button7;
    int selectedDayButtonId, selectedTimeButtonId=-1;
    int selectedDayIndex=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        setTitle("Booking");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        proceed = findViewById(R.id.proceed);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);



        List<DateHelper> dateHelpers = DateGenerator.generateDate();

        button1.setText(dateHelpers.get(0).toString());
        button2.setText(dateHelpers.get(1).toString());
        button3.setText(dateHelpers.get(2).toString());
        button4.setText(dateHelpers.get(3).toString());
        button5.setText(dateHelpers.get(4).toString());
        button6.setText(dateHelpers.get(5).toString());
        button7.setText(dateHelpers.get(6).toString());


        locationAddress = findViewById(R.id.locationAddress);
        locationChange = findViewById(R.id.locationChange);

        /*Setting location*/
        locationAddress.setText(DataTransferHelper.address);
        locationChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationAddress.setEnabled(true);
                locationAddress.requestFocus(MODE_APPEND);

                InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.showSoftInput(locationAddress, InputMethodManager.SHOW_IMPLICIT);


                int position = locationAddress.length();
                Editable editable = (Editable) locationAddress.getText();
                Selection.setSelection(editable, position);

            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Set new address if changes are made*/
                DataTransferHelper.address = locationAddress.getText().toString().trim();

                if (selectedDayIndex ==-1) {
                    Toast.makeText(Booking.this, "Please Select Date", Toast.LENGTH_SHORT).show();
                }else if (selectedTimeButtonId==-1){
                    Toast.makeText(Booking.this, "Please Select Time", Toast.LENGTH_SHORT).show();
                }else{
                    /*Get selected day and time*/
                    Button selectedDayButton = findViewById(selectedDayButtonId);
                    Button selectedTimeButton = findViewById(selectedTimeButtonId);

                    String date = DateGenerator.getDate(selectedDayIndex);
                    Log.i("DATE", date.toString());


                    String selectedTimeString = selectedTimeButton.getText().toString().trim();
                    Log.i("Time", selectedTimeString);

                    DataTransferHelper.dateOfBooking = date;
                    DataTransferHelper.timeOfBooking = selectedTimeString;


                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    startActivity(intent);
                }



            }
        });


        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.one|| id==R.id.two || id==R.id.three || id==R.id.four || id==R.id.five || id==R.id.six || id==R.id.seven || id==R.id.eight ) {

            one.setBackgroundResource(R.drawable.time_button_background);
            two.setBackgroundResource(R.drawable.time_button_background);
            three.setBackgroundResource(R.drawable.time_button_background);
            four.setBackgroundResource(R.drawable.time_button_background);
            five.setBackgroundResource(R.drawable.time_button_background);
            six.setBackgroundResource(R.drawable.time_button_background);
            seven.setBackgroundResource(R.drawable.time_button_background);
            eight.setBackgroundResource(R.drawable.time_button_background);
            //nine.setBackgroundResource(R.drawable.time_button_background);
            one.setTextColor(Color.BLACK);
            two.setTextColor(Color.BLACK);
            three.setTextColor(Color.BLACK);
            four.setTextColor(Color.BLACK);
            five.setTextColor(Color.BLACK);
            six.setTextColor(Color.BLACK);
            seven.setTextColor(Color.BLACK);
            eight.setTextColor(Color.BLACK);


            selectedTimeButtonId = view.getId();
            switch (view.getId())
            {
                case R.id.one:
                    one.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    one.setTextColor(Color.WHITE);
                    return;
                case R.id.two:
                    two.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    two.setTextColor(Color.WHITE);
                    return;
                case R.id.three:
                    three.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    three.setTextColor(Color.WHITE);

                    return;
                case R.id.four:
                    four.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    four.setTextColor(Color.WHITE);

                    return;
                case R.id.five:
                    five.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    five.setTextColor(Color.WHITE);

                    return;
                case R.id.six:
                    six.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    six.setTextColor(Color.WHITE);

                    return;
                case R.id.seven:
                    seven.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    seven.setTextColor(Color.WHITE);

                    return;
                case R.id.eight:
                    eight.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    eight.setTextColor(Color.WHITE);

                    return;

            }
        }else{
            button1.setBackgroundResource(R.drawable.categoty_card_edges);
            button2.setBackgroundResource(R.drawable.categoty_card_edges);
            button3.setBackgroundResource(R.drawable.categoty_card_edges);
            button4.setBackgroundResource(R.drawable.categoty_card_edges);
            button5.setBackgroundResource(R.drawable.categoty_card_edges);
            button6.setBackgroundResource(R.drawable.categoty_card_edges);
            button7.setBackgroundResource(R.drawable.categoty_card_edges);

            button1.setTextColor(Color.BLACK);
            button2.setTextColor(Color.BLACK);
            button3.setTextColor(Color.BLACK);
            button4.setTextColor(Color.BLACK);
            button5.setTextColor(Color.BLACK);
            button6.setTextColor(Color.BLACK);
            button7.setTextColor(Color.BLACK);


            selectedDayButtonId = view.getId();
            switch (view.getId()){
                case R.id.button1:
                    selectedDayIndex=0;
                    button1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    button1.setTextColor(Color.WHITE);
                    return;
                case R.id.button2:
                    selectedDayIndex=1;
                    button2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    button2.setTextColor(Color.WHITE);

                    return;
                case R.id.button3:
                    selectedDayIndex=2;
                    button3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    button3.setTextColor(Color.WHITE);

                    return;
                case R.id.button4:
                    selectedDayIndex=3;
                    button4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    button4.setTextColor(Color.WHITE);

                    return;
                case R.id.button5:
                    selectedDayIndex=4;
                    button5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    button5.setTextColor(Color.WHITE);

                    return;
                case R.id.button6:
                    selectedDayIndex=5;
                    button6.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    button6.setTextColor(Color.WHITE);

                    return;
                case R.id.button7:
                    selectedDayIndex=6;
                    button7.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    button7.setTextColor(Color.WHITE);

                    return;
                default:
                    return;
            }
        }


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
