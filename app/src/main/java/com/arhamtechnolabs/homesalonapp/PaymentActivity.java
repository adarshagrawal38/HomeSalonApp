package com.arhamtechnolabs.homesalonapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arhamtechnolabs.homesalonapp.DataModel.DataHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.DataTransferHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.ListURL;
import com.arhamtechnolabs.homesalonapp.DataModel.SharedPreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PaymentActivity extends AppCompatActivity {

    TextView   totalTextView, checkout;
    final int DISPOSABLE_CHARGES = 0, FEES_TAXES = 0;
    RadioGroup radioGroup;
    int walletBalance, total;
    String phone_no, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setTitle("Payment");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        radioGroup = findViewById(R.id.radioGroup);
        totalTextView = findViewById(R.id.totalTextView);
        checkout = findViewById(R.id.checkout);
        RadioButton radioButton = findViewById(R.id.walletId);

        DataHelper dataHelper = new DataHelper(this);
        final int totalProductCOst = dataHelper.getTotalProductCost();
        int totalServiceCost = dataHelper.getTotalServiceCost();
        total = totalProductCOst + totalServiceCost + DISPOSABLE_CHARGES + FEES_TAXES;
        SharedPreferenceHelper helper = new SharedPreferenceHelper(getApplicationContext());
        walletBalance = helper.getWalletBalance();
        phone_no = helper.getMobile();
        email = helper.getEmail();
        TextView availableBalanceTV = findViewById(R.id.availableBalanceTV);

        totalTextView.setText("Rs. "+String.valueOf(total) + "/-");
        checkout.setText("Pay " + Html.fromHtml("&#x20B9;") +" " + total +"/-");

        String time = DataTransferHelper.timeOfBooking;
        String date =  DataTransferHelper.dateOfBooking;

        String newString = "<font color='#D81B60'>" + String.valueOf(walletBalance) + "</font>";

        availableBalanceTV.setText(availableBalanceTV.getText().toString() +Html.fromHtml("&#x20B9;")+ " "+ String.valueOf(walletBalance));
           final String radioWalletText = radioButton.getText().toString();
        radioButton.setText(radioWalletText);

        Log.i("PaymentTime", time);
        Log.i("PayementDate", date);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedID = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadio =  findViewById(selectedID);

                if (selectedRadio == null) {
                    Toast.makeText(PaymentActivity.this, "Please select payment option", Toast.LENGTH_SHORT).show();
                }else{
                    String text = selectedRadio.getText().toString();
                    if (text.equals("   Cash on Delivery")) {
                        Log.i("CashSelected", "Hello");
                        makeEntryInBooking("Cash");
                        Log.i("Cash", "Done");

                        sendMsg(phone_no);
                        sendMsgOwner();
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.my_dialog, viewGroup, false);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setView(dialogView);
                        final AlertDialog alertDialog = builder.create();

                        Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                        TextView message = dialogView.findViewById(R.id.message);
                        message.setText("Your booking is Successfull.");
                    /*alertDialog.show();
                    buttonOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });*/

                        Intent intent = new Intent(getApplicationContext(), ThankYouPageActivity.class);
                        startActivity(intent);
                        finish();

                    }else if (text.equals(radioWalletText)) {
                        if (walletBalance < total) {
                            Toast.makeText(PaymentActivity.this, "Insuficient Balance", Toast.LENGTH_SHORT).show();
                        }else {

                            ViewGroup viewGroup = findViewById(android.R.id.content);
                            View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.my_dialog, viewGroup, false);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                            builder.setView(dialogView);
                            final AlertDialog alertDialog = builder.create();
                            sendMsg(phone_no);
                            sendMsgOwner();
                            Button buttonOk = dialogView.findViewById(R.id.buttonOk);
                            TextView message = dialogView.findViewById(R.id.message);
                            message.setText("Your booking is Successfull.");
                        /*alertDialog.show();
                        buttonOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });*/

                            Log.i("Wallet", "SufficientBalance");
                            /*Make entry in booking*/
                            updateWallet();
                            Log.i("WalletPay", "Completed");
                            Intent intent = new Intent(getApplicationContext(), ThankYouPageActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }else if (text.equals("   Card Payment/Net Banking  ")){
                        Log.i("Credit", "True");
                        Intent intent = new Intent(getApplicationContext(), PaymentGatewayActivity.class);
                    /*pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);*/
                        intent.putExtra("email", email);
                        intent.putExtra("phone", phone_no);
                        intent.putExtra("amount", String.valueOf(total));
                        startActivity(intent);
                        finish();
                    }
                    else if(text.equals("   Redeem Coupon  ")){
                        makeEntryInBooking("Coupon Code");
                        sendMsg(phone_no);
                        sendMsgOwner();
                        Intent intent = new Intent(getApplicationContext(), ThankYouPageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(text.equals("   Refer And Earn  ")){
//                        Toast.makeText(PaymentActivity.this, "Referal Code", Toast.LENGTH_SHORT).show();
//                        makeEntryInBooking("Referal Code");
                        SharedPreferenceHelper helper1  = new SharedPreferenceHelper(getApplicationContext());
                         String s = helper1.getReferCode();

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Download Home Salon Application and get Exciting Rewards and Offers. Use my Referral code to get more 25. \n My Code:" + s);
                        sendIntent.setPackage("com.whatsapp");
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                    else {
                        Toast.makeText(PaymentActivity.this, "Please Select Payment Mode", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

    }

    private void updateWallet() {
        String url = ListURL.PAY_VIA_WALLET + total + "&phone_no="+phone_no;

        Log.i("URL_BeforePassing", url);
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    System.out.println(response);
                    JSONArray jsonArray = object.getJSONArray("data");
                    System.out.println(jsonArray);
                    Log.i("URL_INSIDE", "Entry_MAde");
                    makeEntryInBooking("Wallet");

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

    private void makeEntryInBooking(String mode) {
        DataHelper dataHelper = new DataHelper(this);

        String category = dataHelper.categoryList();
        /*user_id date SubSubCat_ID amount payment_mode;*/
        String d = DataTransferHelper.dateOfBooking;
        String a = String.valueOf(total);

        String address = DataTransferHelper.address;
        System.out.println("makeEntryAddress " + address);

        String t =  DataTransferHelper.timeOfBooking;
        String url = ListURL.BOOKING_INSERT_URL;
        String encodedUrl="";
        SharedPreferenceHelper helper = new SharedPreferenceHelper(getApplicationContext());
        String user_id =  helper.getUserId();

        try {
            //encodedUrl = URLEncoder.encode(address, "UTF-8");
            //address = encodedUrl;

            encodedUrl = URLEncoder.encode(category, "UTF-8");
            //category = encodedUrl;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        url+="user_id="+user_id+"&date="+d+"&SubSubCat_ID="+category+"&amount="+a+"&payment_mode="+mode+"&booking_time="+t+"&address="+address;
        // url+="user_id="+user_id+"&date="+d+"&SubSubCat_ID="+"sfsfz"+"&amount="+"sfesf"+"&payment_mode="+""+"&booking_time="+"fsf"+"&address="+"fsdfsf";
        url = url.replace(" ", "%20");
        url= url.replace(",", "%2C");

        Log.i("URL_BeforePassing", url);
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    System.out.println(response);
                    JSONArray jsonArray = object.getJSONArray("data");
                    System.out.println(jsonArray);
                    Log.i("URL_INSIDE", "Entry_MAde");

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

        /*Clear cart*/
        dataHelper.flushTable();

        Log.i("BookingActivity", "CartTableDeleted");

    }

    public void sendMsg(String mob) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://www.fast2sms.com/dev/bulk?authorization=rDJNoQ1OMc6inxbBVvE0PTWFIlHG7UsdC8jyLK34gZ9eXYp5R2S41uhNpmvEXHtUfFszLJRCeADYklog&sender_id=FSTSMS&message=Your%20Booking%20is%20Confirmed.%20You%20can%20check%20in%20Appoinement%20Section.%20&language=english&route=p&numbers=" +mob;

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR","error => "+error.toString());

                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("cache-control", "no-cache");
//                params.put("Accept-Language", "fr");

                return params;
            }
        };
        queue.add(stringRequest);

    };

    public void sendMsgOwner() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://www.fast2sms.com/dev/bulk?authorization=rDJNoQ1OMc6inxbBVvE0PTWFIlHG7UsdC8jyLK34gZ9eXYp5R2S41uhNpmvEXHtUfFszLJRCeADYklog&sender_id=FSTSMS&message=You%20have%20new%20Booking.%20Kindly%20check%20admin%20login%20for%20Details.%20&language=english&route=p&numbers=7017060171";

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR","error => "+error.toString());

                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("cache-control", "no-cache");
//                params.put("Accept-Language", "fr");

                return params;
            }
        };
        queue.add(stringRequest);

    };
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
