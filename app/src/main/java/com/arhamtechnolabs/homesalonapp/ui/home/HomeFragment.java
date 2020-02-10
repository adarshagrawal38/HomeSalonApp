package com.arhamtechnolabs.homesalonapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arhamtechnolabs.homesalonapp.Adapters.BannerAdapter;
import com.arhamtechnolabs.homesalonapp.Adapters.CategoryAdapter;
import com.arhamtechnolabs.homesalonapp.Adapters.FeedbackAdapter;
import com.arhamtechnolabs.homesalonapp.Adapters.PackageAdapter;
import com.arhamtechnolabs.homesalonapp.Adapters.RecommandedServiceAdapter;
import com.arhamtechnolabs.homesalonapp.ChckoutActivity;
import com.arhamtechnolabs.homesalonapp.DataModel.BannerMaster;
import com.arhamtechnolabs.homesalonapp.DataModel.Category;
import com.arhamtechnolabs.homesalonapp.DataModel.DataHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.FeedbackMaster;
import com.arhamtechnolabs.homesalonapp.DataModel.ListURL;
import com.arhamtechnolabs.homesalonapp.DataModel.SharedPreferenceHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.SubSubCategory;
import com.arhamtechnolabs.homesalonapp.DateHelper;
import com.arhamtechnolabs.homesalonapp.LoginActivity;
import com.arhamtechnolabs.homesalonapp.NewActivity;
import com.arhamtechnolabs.homesalonapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class HomeFragment extends Fragment {


    private RecyclerView rv_banner, categoryGrid, rv_testimonials, rv_recommanded_services;
    View root;
    //Lists
    private List<BannerMaster> listBanner;
    private List<FeedbackMaster> listFeedback;
    private List<Category> catList;
    String SUB_SUB_CAT_URL = ListURL.VIEW_SUB_SUB_CATEGORY;
    static TextView itemCountTv;
    static Context context;

    private RecyclerView.Adapter adapter;

   //URL
    private String HttpURLBANNER = ListURL.VIEW_BANNER;
    private String HttpURL = ListURL.VIEW_FEEDBACK;
    private String HttpURLCATEGORY = ListURL.VIEW_CATEGORY;

    List<SubSubCategory> subSubCategories;
    ImageView shoppingcartactivityid;

    EditText searchED;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        itemCountTv = root.findViewById(R.id.itemCountTv);

        context = getActivity();
        getActivity().findViewById(R.id.addressTV).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.descTV).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.loc_img).setVisibility(View.VISIBLE);
        TextView titleTV = getActivity().findViewById(R.id.titleTV);
        titleTV.setVisibility(View.GONE);
        searchED = root.findViewById(R.id.searchED);

//        titleTV.setText("Privacy Policy");

        rv_banner = root.findViewById(R.id.rv_banner);
        categoryGrid = root.findViewById(R.id.categoryGrid);
        rv_testimonials = root.findViewById(R.id.rv_testimonials);
        rv_recommanded_services = root.findViewById(R.id.rv_recommanded_services);
        shoppingcartactivityid = root.findViewById(R.id.shoppingcartactivityid);

        listBanner = new ArrayList<>();
        listFeedback = new ArrayList<>();
        catList = new ArrayList<>();
        subSubCategories = new ArrayList<>();

        updateCart();
        loadData();
        loadBanner();
        loadCategory();
        loadSubSubCategory(1);

        shoppingcartactivityid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(getActivity());
                if (sharedPreferenceHelper.getMobile().equals("")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }else {

                    DataHelper dataHelper = new DataHelper(context);
                    int total = dataHelper.getTotalServiceCost();
                    if (total>0) {
                        Intent intent = new Intent(getActivity(), ChckoutActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(context, "Your cart is empty", Toast.LENGTH_LONG).show();
                    }

                }


            }
        });

        searchED.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(getActivity(), NewActivity.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().finish();

                return false;
            }
        });

        return root;
    }


    public void updateCart() {

         Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DataHelper dataHelper = new DataHelper(context);
                int itemCount = dataHelper.getItemCount();
                itemCountTv.setText(String.valueOf(itemCount));

            }
        });
        thread.start();

        //thread.stop();


    }

    @Override
    public void onResume() {
        super.onResume();
        //thread.interrupt();
        DataHelper dataHelper = new DataHelper(getActivity());
        int itemCount = dataHelper.getItemCount();
        itemCountTv.setText(String.valueOf(itemCount));
        //updateCart();

    }

    private void loadCategory() {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURLCATEGORY ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    System.out.println(response);
                    JSONArray jsonArray = object.getJSONArray("data");
                    Log.d("JSON ARRAY CAT :", String.valueOf(jsonArray));

                    System.out.println(jsonArray);

                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        System.out.println(jsonObject);

                        Category item = new Category(
                                jsonObject.getString("catID"),
                                jsonObject.getString("cat_img"),
                                jsonObject.getString("cat_name"),
                                jsonObject.getString("cat_city"),
                                jsonObject.getString("cat_ratePerMin")
                        );
                        catList.add(item);
                    }

                    adapter = new CategoryAdapter(getActivity(),catList);
                    categoryGrid.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false));
                    categoryGrid.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void loadData() {

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    System.out.println(response);
                    JSONArray jsonArray = object.getJSONArray("data");

                    System.out.println(jsonArray);

                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        System.out.println(jsonObject);

                        FeedbackMaster item = new FeedbackMaster(
                                jsonObject.getString("name"),
                                jsonObject.getString("rating"),
                                jsonObject.getString("comments"),
                                jsonObject.getString("description")
                        );
                        listFeedback.add(item);
                    }

                    adapter = new FeedbackAdapter(getActivity(),listFeedback);
                    rv_testimonials.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));

                    rv_testimonials.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    private void loadBanner() {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURLBANNER,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    System.out.println(response);
                    JSONArray jsonArray = object.getJSONArray("data");

                    System.out.println(jsonArray);

                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        System.out.println(jsonObject);

                        BannerMaster item = new BannerMaster(
                                jsonObject.getString("voucher_img")
                        );
                        listBanner.add(item);
                    }

                    adapter = new BannerAdapter(getActivity(),listBanner);
                    rv_banner.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));

                    rv_banner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void loadSubSubCategory(int x) {

        String new_sub_sub_cat_url = SUB_SUB_CAT_URL + x;
        System.out.println("url : " + new_sub_sub_cat_url);
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, new_sub_sub_cat_url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    System.out.println(response);
                    JSONArray jsonArray = object.getJSONArray("data");

                    System.out.println(jsonArray);
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
                        adapter = new RecommandedServiceAdapter(getActivity(), subSubCategories);
                        rv_recommanded_services.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                        rv_recommanded_services.setAdapter(adapter);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}