package com.arhamtechnolabs.homesalonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arhamtechnolabs.homesalonapp.Adapters.FilterAdapter;
import com.arhamtechnolabs.homesalonapp.Adapters.PackageAdapter;
import com.arhamtechnolabs.homesalonapp.DataModel.ListURL;
import com.arhamtechnolabs.homesalonapp.DataModel.SubCategory;
import com.arhamtechnolabs.homesalonapp.DataModel.SubSubCategory;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewActivity extends AppCompatActivity {


    EditText searchEDnew;
    List<SubSubCategory> subSubCategories, imutablesubSubCategories;
    String subCatNameString = "", subCatIDString = "";
    RecyclerView filterData;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        setTitle("Search...");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        searchEDnew = findViewById(R.id.searchEDnew);
        subSubCategories = new ArrayList<>();
        imutablesubSubCategories = new ArrayList<>();
        filterData = findViewById(R.id.filterData);

        loadSubSubCategory();

        searchEDnew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("charseq", charSequence.toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String search = charSequence.toString().toLowerCase();
                List<SubSubCategory> newList = new ArrayList<>();

                /*Puplating intial data in the list before change*/
                subSubCategories.clear();
                subSubCategories.addAll(imutablesubSubCategories);
                for (SubSubCategory d: subSubCategories){
                    String item = d.getSubSubCat_Name().toLowerCase();
                    if(item.contains(search)) {
                        newList.add(d);
                    }
                }
                subSubCategories.clear();
                subSubCategories.addAll(newList);
                if (adapter!=null){

                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    private void loadSubSubCategory() {

        String new_sub_sub_cat_url = ListURL.FETCH_ALL_SUB_SUB_CAT;

        System.out.println("url : " + new_sub_sub_cat_url);

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, new_sub_sub_cat_url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    System.out.println(response);
                    JSONArray jsonArray = object.getJSONArray("data");

                    System.out.println(jsonArray);
                    subSubCategories.clear();
                    for (int i = 0;i<jsonArray.length();i++){
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
                        imutablesubSubCategories.add(item);

                        adapter = new FilterAdapter(getApplicationContext(), subSubCategories);
                        filterData.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        filterData.setAdapter(adapter);

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

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }


}
