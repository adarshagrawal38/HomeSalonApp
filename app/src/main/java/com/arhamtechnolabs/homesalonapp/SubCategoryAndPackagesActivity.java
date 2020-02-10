package com.arhamtechnolabs.homesalonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arhamtechnolabs.homesalonapp.Adapters.PackageAdapter;
import com.arhamtechnolabs.homesalonapp.DataModel.CartData;
import com.arhamtechnolabs.homesalonapp.DataModel.DataHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.ListURL;
import com.arhamtechnolabs.homesalonapp.DataModel.SharedPreferenceHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.SubCategory;
import com.arhamtechnolabs.homesalonapp.DataModel.SubSubCategory;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryAndPackagesActivity extends AppCompatActivity {


    TabLayout tablayoutForSubCat;
    String subCatID, subCatName;
    String[] sub_cat_nameArray, sub_cat_IdArray;
    String SUB_CAT_URL = ListURL.VIEW_SUBCATEGORY;
    List<SubCategory> subCategoryList;
    Bundle bundle;
    String subCatNameString = "", subCatIDString = "";
    List<SubSubCategory> subSubCategories = new ArrayList<>();
    String SUB_SUB_CAT_URL = ListURL.VIEW_SUB_SUB_CATEGORY;
    RecyclerView packageRecycler;
    private RecyclerView.Adapter adapter;
    TextView checkout, time, rs, count_box, itemCountTv_2;
    FrameLayout subCartBtn;
    TextView notificationCartTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_and_packages);
        setTitle("Select Package");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tablayoutForSubCat = findViewById(R.id.tablayoutForSubCat);
        packageRecycler = findViewById(R.id.packageRecycler);
        checkout = findViewById(R.id.checkout1);
        count_box = findViewById(R.id.count_box);
        rs = findViewById(R.id.rs1);
        itemCountTv_2 = findViewById(R.id.itemCountTv_2);

        subCartBtn = findViewById(R.id.subCartBtn);
        subCategoryList = new ArrayList<>();
        bundle = getIntent().getExtras();

        String id = bundle.getString("id");
        loadSubCategory(id);

        subCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataHelper dataHelper = new DataHelper(getApplicationContext());
                List<CartData> list = dataHelper.fetchRequest();
                int totalPrice = dataHelper.getTotalServiceCost();
                if (totalPrice > 0) {
                    Intent intent = new Intent(getApplicationContext(), ChckoutActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SubCategoryAndPackagesActivity.this, "Cart is empty", Toast.LENGTH_LONG).show();
                }
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceHelper helper = new SharedPreferenceHelper(getApplicationContext());
                if (helper.getMobile().equals("")) {
                    //user should first login
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    DataHelper dataHelper = new DataHelper(getApplicationContext());
                    List<CartData> list = dataHelper.fetchRequest();
                    int totalPrice = dataHelper.getTotalServiceCost();
                    if (totalPrice > 0) {
                        Intent intent = new Intent(getApplicationContext(), ChckoutActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SubCategoryAndPackagesActivity.this, "Cart is empty", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        DataHelper dataHelper = new DataHelper(this);
        List<CartData> list = dataHelper.fetchRequest();

        int totalTime = 0;
        int totalPrice = 0;
        int count = 0;
        for (CartData cartData : list) {
            totalPrice += cartData.getTotalAmount();
            totalTime += cartData.getTimeRequired();
            count += cartData.getNumberOfTime();
        }

//        time.setText("Total Time - "+ String.valueOf(totalTime));
        rs.setText("" + Html.fromHtml("&#x20B9;") + " " + String.valueOf(totalPrice));
        count_box.setText(String.valueOf(count));
        itemCountTv_2.setText(String.valueOf(count));

//        checkout.setText("Checkout(" + String.valueOf(count) + ")");
    }


    private void loadSubCategory(String id) {

        String new_id = SUB_CAT_URL + id;

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, new_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    System.out.println(response);
                    JSONArray jsonArray = object.getJSONArray("data");

                    System.out.println(jsonArray);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        System.out.println(jsonObject);

                        SubCategory item = new SubCategory(
                                jsonObject.getString("sub_cat_id"),
                                jsonObject.getString("sub_cat_name")
                        );

                        subCategoryList.add(item);

                        subCatNameString += subCategoryList.get(i).getSub_cat_name().toString() + ",";
                        subCatIDString += subCategoryList.get(i).getSub_cat_id().toString() + ",";
                        System.out.println("111 : " + subCatNameString);

                    }
                    splitter(subCatNameString, subCatIDString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void splitter(String subCatNameString, String subCatIDString) {
        sub_cat_nameArray = subCatNameString.split(",");
        sub_cat_IdArray = subCatIDString.split(",");
        for (int i = 0; i < sub_cat_nameArray.length; i++) {
            tablayoutForSubCat.addTab(tablayoutForSubCat.newTab().setText(sub_cat_nameArray[i]));
            tablayoutForSubCat.setTag(sub_cat_IdArray[i]);
        }
        if (tablayoutForSubCat.getTabCount() == 3) {
            tablayoutForSubCat.setTabMode(TabLayout.MODE_FIXED);
        } else {
            tablayoutForSubCat.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        loadSubSubCategory(Integer.parseInt(sub_cat_IdArray[0]));
        System.out.println();

        tablayoutForSubCat.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("Tab Selected");
                int x = tablayoutForSubCat.getSelectedTabPosition();
                //System.out.println("Tab Selected tag " + tab.getTag().toString());
                int sub_sub_id = Integer.parseInt(sub_cat_IdArray[x]);
                System.out.println("SUB_SUB_CAT_ID id : " + sub_sub_id);
                loadSubSubCategory(sub_sub_id);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void method() {

        /*Showing Details in below SUB SUB Category*/
        DataHelper dataHelper = new DataHelper(this);
        List<CartData> list = dataHelper.fetchRequest();

        Log.i("Debug", String.valueOf(list.size()));
        int totalTime = 0;
        int totalPrice = 0;
        int count = 0;
        for (CartData cartData : list) {
            totalPrice += cartData.getTotalAmount();
            totalTime += cartData.getTimeRequired();
            count += cartData.getNumberOfTime();

        }

        //time.setText("Total Time - "+ String.valueOf(totalTime));
        rs.setText("" + Html.fromHtml("&#x20B9;") + " " + String.valueOf(totalPrice));
        count_box.setText(String.valueOf(count));
        itemCountTv_2.setText(String.valueOf(count));
        notificationCartTextView.setText(String.valueOf(count));

    }


    private void loadSubSubCategory(int x) {

        String new_sub_sub_cat_url = SUB_SUB_CAT_URL + x;

        System.out.println("url : " + new_sub_sub_cat_url);

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, new_sub_sub_cat_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    System.out.println(response);
                    JSONArray jsonArray = new JSONArray();
                    try {
                        jsonArray = object.getJSONArray("data");
                    }catch (Exception e) {
                        System.out.println("Error " + e.getMessage());
                    }

                    System.out.println(jsonArray);
                    subSubCategories.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        System.out.println(jsonObject);

                        SubSubCategory item = new SubSubCategory(
                                jsonObject.getString("SubSubCat_ID"),
                                jsonObject.getString("SubSubCat_Name"),
                                jsonObject.getString("SubSubCat_price"),
                                jsonObject.getString("SubSubCat_timeInMin"),
                                jsonObject.getString("SubSubCat_discount"),
                                jsonObject.getString("SubSubCat_features"),
                                jsonObject.getString("subsubcategory_image"),
                                jsonObject.getString("SubSubCatProductCost"),
                                jsonObject.getString("SubSubuCatSuggestions")

                        );

                        System.out.println("SubSub cat : " + item.toString());
                        subSubCategories.add(item);



                    }
                    adapter = new PackageAdapter(getApplicationContext(), subSubCategories);
                    packageRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    packageRecycler.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        loadSubSubCategory(Integer.parseInt(sub_cat_IdArray[0]));


        /*UPDATE RECYCLER VIEWER */

        /*for (RecyclerView.Adapter a: adapters) {
            a.notifyDataSetChanged();
        }*/
        adapter.notifyDataSetChanged();
        Log.i("Debug", "BackAction");
        method();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        MenuItem item = menu.findItem(R.id.cartButton);
        MenuItemCompat.setActionView(item, R.layout.actionbar_badge_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);

        DataHelper dataHelper = new DataHelper(this);
        List<CartData> list = dataHelper.fetchRequest();
        int totalTime = 0;
        int totalPrice = 0;
        int count = 0;
        for (CartData cartData : list) {
            totalPrice += cartData.getTotalAmount();
            totalTime += cartData.getTimeRequired();
            count += cartData.getNumberOfTime();
        }

         notificationCartTextView =  notifCount.findViewById(R.id.actionbar_notifcation_textview);
        notificationCartTextView.setText(String.valueOf(count));

        return super.onCreateOptionsMenu(menu);

    }
}
