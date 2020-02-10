package com.arhamtechnolabs.homesalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class GiftVoucherPerRow extends AppCompatActivity {
    ImageView imageGiftVoucher;
    TextView price, note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_voucher_per_row);

        imageGiftVoucher = findViewById(R.id.imageGiftVoucher);
        price = findViewById(R.id.price);
        note = findViewById(R.id.note);

        Intent intent = getIntent();
        intent.getStringExtra("img_string");

        Glide.with(getApplicationContext()).load(intent.getStringExtra("img_string")).into(imageGiftVoucher);
        price.setText("Just Rs " +intent.getStringExtra("price") + "/-");
        note.setText("Note: "+intent.getStringExtra("notes"));
    }
}
