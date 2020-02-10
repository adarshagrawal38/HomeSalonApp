package com.arhamtechnolabs.homesalonapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.arhamtechnolabs.homesalonapp.DataModel.DataHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.DataTransferHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.SharedPreferenceHelper;
import com.arhamtechnolabs.homesalonapp.DataModel.UserHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    TextView descTV;
    LocationManager locationManager;
    LocationListener locationListener;
    private FusedLocationProviderClient fusedLocationClient;
    String addressLine = "";
    String city = "";
    public static final String LOCATION = "locationKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    private static int REQUEST_PHONE_CALL = 1001;
    NavController navController;
    Toolbar toolbar;
    LinearLayout linearToolbar;
    DrawerLayout drawer;
    ImageView notification;
    ProgressDialog progressDoalog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        descTV = toolbar.findViewById(R.id.descTV);
        linearToolbar = toolbar.findViewById(R.id.linearToolbar);
        notification = toolbar.findViewById(R.id.notification);



        /*Getting user ID*/
        UserHelper userHelper = new UserHelper(getApplicationContext());
        userHelper.setUserID();

        SharedPreferenceHelper helper = new SharedPreferenceHelper(getApplicationContext());
        String userName = helper.getUserName();

        /*Set username*/
        TextView userNameTextView = findViewById(R.id.userNameTextView);
        System.out.println("Username : " + userName);
//        userNameTextView.setText( userName);

        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();



        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_franchise,
                R.id.nav_tieUP, R.id.nav_giftVoucher, R.id.nav_faq, R.id.nav_privacy_policy, R.id.nav_wallet,
                R.id.nav_tools, R.id.nav_referFriend)
                .setDrawerLayout(drawer)
                .build();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);


        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());

                setAddressAndCity(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT > 23) {

            //Before MarshMello
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            }else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                setAddressAndCity(location);

            }

        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            setAddressAndCity(location);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

//        System.out.println("Hello world");
//        locationManager = (LocationManager) this.getSystemService(LocationManager.GPS_PROVIDER);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        System.out.println("LAST COCATIOn");
                        if (location != null) {
                            Log.i("last know location", location.toString());
                            setAddressAndCity(location);
                        }
                    }
                });

        descTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        descTV.setHorizontallyScrolling(true);
        descTV.setSelected(true);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "No New Notification Available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                System.out.println("Hello1");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    SharedPreferences sharedpreferences;

    void setAddressAndCity(Location location) {

        if (location!= null){
            DataTransferHelper.location = location;
            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());

            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(sydney.latitude, sydney.longitude, 1);
                if (addresses != null && addresses.size() > 0) {
                    Log.i("Address", addresses.get(0).toString());

                    city = addresses.get(0).getSubAdminArea();
                    addressLine = addresses.get(0).getAddressLine(0);
                    System.out.println("City: "+ city);
                    System.out.println("AddressLine: " + addressLine);
                    TextView textView = toolbar.findViewById(R.id.descTV);
                    textView.setText(addressLine);
                    textView.setSelected(true);

                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(LOCATION, addressLine);
                    editor.apply();

                    if (DataTransferHelper.address != null){
                        DataTransferHelper.address = addressLine;
                    }

                    DataTransferHelper.address = addressLine;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (DataTransferHelper.address != null){
            Log.d("Data helper on resume", DataTransferHelper.address);
            String x = DataTransferHelper.address;
            descTV.setText(x);

        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        System.out.println("in call us1");

        if (id == R.id.callUs) {
            System.out.println("in call us2");

            Intent intent = new Intent(Intent.ACTION_CALL);

            intent.setData(Uri.parse("tel:" + "+917017060171"));
            System.out.println("in call us");
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            }
            else
            {
                startActivity(intent);
            }
        }else if(id == R.id.nav_profile){
            Intent intent = new Intent(MainActivity.this, ProfileActivity2.class);
            startActivity(intent);
        }else if(id == R.id.nav_signOut){
            SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();

            DataHelper dataHelper = new DataHelper(getApplicationContext());
            dataHelper.flushTable();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else
        {
            // Make your navController object final above
            // or call Navigation.findNavController() again here
            NavigationUI.onNavDestinationSelected(menuItem, navController);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
