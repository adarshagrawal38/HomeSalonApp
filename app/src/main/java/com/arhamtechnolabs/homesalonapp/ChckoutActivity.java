package com.arhamtechnolabs.homesalonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arhamtechnolabs.homesalonapp.Adapters.CheckoutAdapter;
import com.arhamtechnolabs.homesalonapp.DataModel.CartData;
import com.arhamtechnolabs.homesalonapp.DataModel.DataHelper;

import java.util.List;

public class ChckoutActivity extends AppCompatActivity implements View.OnClickListener {


    SharedPreferences sharedpreferences;
    TextView checkout, value_rs;
    RecyclerView checkoutRecycler;
    RecyclerView.Adapter adapter;
    DataHelper dataHelper;

    TextView numberPickerDisplayTv;
    Button numberPickerDecrementBtn, numberPickerIncrementBtn;
    public static final int DECREMENT_BTN = R.id.decrement;
    public static final int INCREMENT_BTN = R.id.increment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chckout);
        setTitle("Checkout");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*Number picker finding them*/
/*
        numberPickerDisplayTv = findViewById(R.id.display);
        numberPickerDecrementBtn = findViewById(R.id.decrement);
        numberPickerIncrementBtn = findViewById(R.id.increment);*/

        //numberPickerIncrementBtn.setOnClickListener(this);
        //numberPickerDecrementBtn.setOnClickListener(this);


        value_rs = findViewById(R.id.value_rs);

        checkoutRecycler = findViewById(R.id.checkoutRecycler);
        checkout = findViewById(R.id.checkout);
         dataHelper = new DataHelper(getApplicationContext());

        List<CartData> cartData = dataHelper.fetchRequest();
        adapter = new CheckoutAdapter(getApplicationContext(), cartData);

        checkoutRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        checkoutRecycler.setAdapter(adapter);
    }

    public void method2(final float total) {

//        checkout.setText("Proceed to Payment (" + total + ")");

        value_rs.setText("Rs. " + total + " ");
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*First check is there is item in cart*/

                List<CartData> cartData = dataHelper.fetchRequest();
                if (cartData==null || cartData.size()==0) {
                    Toast.makeText(ChckoutActivity.this, "No item in cart!", Toast.LENGTH_LONG).show();
                }else{

                    Intent intent= new Intent(getApplicationContext(), Booking.class);
                    intent.putExtra("amountFinal" ,String.valueOf(total));
                    startActivity(intent);
                }

            }
        });
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

    @Override
    public void onClick(View view) {
        int currentNumber = Integer.parseInt(numberPickerDisplayTv.getText().toString());

        int id = view.getId();
        int newNumber = currentNumber;
        if (id == DECREMENT_BTN) {
            newNumber--;
        }else{

            /*Increment  button pressed*/
            newNumber++;

        }
        numberPickerDisplayTv.setText(String.valueOf(newNumber));

    }
}
