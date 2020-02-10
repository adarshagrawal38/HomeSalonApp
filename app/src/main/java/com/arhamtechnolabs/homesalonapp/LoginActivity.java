package com.arhamtechnolabs.homesalonapp;

import androidx.annotation.Nullable;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arhamtechnolabs.homesalonapp.DataModel.SharedPreferenceHelper;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {


    EditText mobile_no;
    Button submitNo;
    String mob;
    Button skipBtn;
    private static int RC_SIGN_IN = 9001;
    public static boolean GOOGLE_SIGN_IN = false;


    private SignInButton sign_in_button;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        sign_in_button = findViewById(R.id.sign_in_button);
        sign_in_button.setSize(SignInButton.SIZE_STANDARD);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        login_button = findViewById(R.id.login_button);
        mobile_no = findViewById(R.id.mobile_no);
        submitNo = findViewById(R.id.submitNo);

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        submitNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mob = mobile_no.getText().toString().trim();

                sendOTP(mob);

            }
        });

        skipBtn = findViewById(R.id.skip);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


//        callbackManager = CallbackManager.Factory.create();
//        login_button.setReadPermissions(Arrays.asList("email","public_profile"));
//        LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("public_profile"));
//        checkLoginStatus();

//        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void googlePref(GoogleSignInAccount account) {
        GOOGLE_SIGN_IN = true;

        Intent intent  = new Intent(LoginActivity.this, ProfileActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
//            updateUI(account);
            System.out.println("EMAIL " + account.getEmail().toString());

            String email = account.getEmail();
            String userName = account.getDisplayName();
            SharedPreferenceHelper helper = new SharedPreferenceHelper(getApplicationContext());
            helper.setEmail(email);
            helper.setUsername(userName);

            Toast.makeText(this, "Success : " + account.getEmail(), Toast.LENGTH_SHORT).show();
            System.out.println(account.toString());
            googlePref(account);

//            sendToMain();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error Log : ", "SignIn Result:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }


    private void sendOTP(String mob) {
        Random rand = new Random();

        // Generate random integers in range 0 to 999
        String otpString = "";
        for (int i=0;i<6;i++) {
            otpString+=String.valueOf(rand.nextInt(10));
        }

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://www.fast2sms.com/dev/bulk?authorization=rDJNoQ1OMc6inxbBVvE0PTWFIlHG7UsdC8jyLK34gZ9eXYp5R2S41uhNpmvEXHtUfFszLJRCeADYklog&sender_id=FSTSMS&message=You%20otp%20for%20logIn%20is%20"+otpString+"&language=english&route=p&numbers=" +mobile_no.getText().toString();

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


            Intent intent = new Intent(LoginActivity.this, OtpScreen.class);

            intent.putExtra("mobileno", mob);
            intent.putExtra("otp", otpString);
            startActivity(intent);
            finish();
        };



    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        callbackManager.onActivityResult(requestCode, resultCode,data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }

//    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
//        @Override
//        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//            if (currentAccessToken == null){
//                Toast.makeText(LoginActivity.this, "User Logged Out", Toast.LENGTH_SHORT).show();
//            }else{
//                loadUserProfile(currentAccessToken);
//            }
//        }
//    };

//    private void loadUserProfile(AccessToken accessToken ){
//
//        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                try {
//                    String first_name = object.getString("first_name");
//                    String last_name = object.getString("last_name");
//                    String email = object.getString("email");
//                    String id = object.getString("id");
////                    String first_name = object.getString("first_name");
//
//                    Log.d("email, name, id", first_name + last_name + email);
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        Bundle bundle = new Bundle();
//        bundle.putString("fields", "first_name,last_name,email,id");
//        graphRequest.setParameters(bundle);
//        graphRequest.executeAsync();
//
//    }

//    private void checkLoginStatus(){
//
//        if (AccessToken.getCurrentAccessToken() != null){
//            loadUserProfile(AccessToken.getCurrentAccessToken());
//        }
//
//    }


