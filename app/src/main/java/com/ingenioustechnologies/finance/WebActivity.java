package com.ingenioustechnologies.finance;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.ingenioustechnologies.finance.api.ApiClient;
import com.ingenioustechnologies.finance.api.ApiInterface;
import com.ingenioustechnologies.finance.model.TrackRes;

import br.com.safety.locationlistenerhelper.core.CurrentLocationListener;
import br.com.safety.locationlistenerhelper.core.CurrentLocationReceiver;
import br.com.safety.locationlistenerhelper.core.LocationTracker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    SharedPreferences sharedpreferences;
    public static final String Name = "nameKey";
    public static final String PWD = "passwordKey";
    public static final String Uid = "useridKey";
    public static final String Userrole = "userroleKey";
    public static final String Vkey = "versionKey";
    public static final String mypreference = "financesharedpref";
    int once = 0;
    String m2, username, password, url,muserid;
    LottieAnimationView load, nosignal;
    WebView wv_webview;
//    FingerprintDialogBuilder dialogBuilder;
    LocationTracker locationTracker;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        url = intent.getStringExtra("url");

        load = findViewById(R.id.f_mainload);

        nosignal = findViewById(R.id.f_mainnosignal);
        load.setVisibility(View.VISIBLE);

        m2 = "http://projecting.ingenious-technologies.com/finance_app/" + url;
        wv_webview = (WebView) findViewById(R.id.appview);
        wv_webview.getSettings().setJavaScriptEnabled(true);
        wv_webview.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return true;
//            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @SuppressLint("JavascriptInterface")
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                wv_webview.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('main-sidebar')[0].style.display='none'; " +
                        "document.getElementsByClassName('sidebar-toggle')[0].style.display='none';" +
                        "document.getElementsByClassName('logo')[0].style.display='none'; " +
                        "document.getElementsByClassName('logout')[0].style.display='none';" +
                        "document.getElementById('exportBorrowers').style.display='none';" +
                        "document.getElementById('exportLoans').style.display='none';" +
                        "document.getElementById('exportDueReport').style.display='none';})()");

//                Button btnLogin=new Button(getApplicationContext());
//                wv_webview.addJavascriptInterface(btnLogin,"login");
//                btnLogin.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    @JavascriptInterface
//                    public void onClick(View v) {
//                        Toast.makeText(getApplicationContext(),"verified button clicked",Toast.LENGTH_LONG).show();
//                    }
//                });

                wv_webview.addJavascriptInterface(new Object() {
                    @JavascriptInterface
                    public void performClick(String wuserid) throws Exception //method which you call on button click on HTML page
                    {
                        Log.d("LOGIN::", "Clicked");
                        Log.d("username",wuserid);
                        muserid=wuserid;
//                        if(sharedpreferences.getString(Vkey,null).equals("yes")){
//                            if (sharedpreferences.getString(Userrole, null).equals("user")) {
//                                checkfinger();
//                            }
//                        }else{
                            if (sharedpreferences.getString(Userrole, null).equals("user")) {
                                checkdisplay();
                            }
//                        }

//                        Toast.makeText(getApplicationContext(), "Login clicked", Toast.LENGTH_LONG).show();
                    }
                }, "btnLogin");


                if (once == 0) {

                    try {
                        wv_webview.loadUrl(m2);
                    } catch (Exception ru) {
                        ru.printStackTrace();
                        Toast.makeText(getApplicationContext(), "try again later", Toast.LENGTH_LONG).show();
                    }
                    load.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Please Wait....!", Toast.LENGTH_LONG).show();
                    wv_webview.setVisibility(View.VISIBLE);

                    once = 1;
                }

            }
        });
        //wv_webview.setWebViewClient(new WebViewClient());
//        String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
//        wv_webview.getSettings().setUserAgentString(newUA);
        String linkss = "http://projecting.ingenious-technologies.com/finance_app/login?username=" + username + "&password=" + password + "";
//        Log.d("webviewtest", m2);

        wv_webview.setVisibility(View.INVISIBLE);
        load.setVisibility(View.VISIBLE);
        try {
            wv_webview.loadUrl(linkss);
        } catch (Exception r) {
            r.printStackTrace();
            Toast.makeText(getApplicationContext(), "try again later", Toast.LENGTH_LONG).show();
        }

    }
//todo for real finger print auth but not here
//    public void checkfinger() {
//        try {
//            dialogBuilder = new FingerprintDialogBuilder(WebActivity.this)
//                    .setTitle("Verification")
//                    .setSubtitle("Verify Customer Address")
//                    .setDescription("Customer Address Verification")
//                    .setNegativeButton("Cancel");
//            FragmentManager fm = getSupportFragmentManager();
//            dialogBuilder.show(fm, callback);
//
//
//        } catch (Exception m) {
//            m.printStackTrace();
//        }
//    }
    public void checkdisplay(){
        final AlertDialog alertDialog = new AlertDialog.Builder(WebActivity.this).setTitle("Verification").create();

        alertDialog.show();
        Window win = alertDialog.getWindow();
        win.setContentView(R.layout.new_layout);

        //Game
        ImageButton game_btn = (ImageButton)win.findViewById(R.id.finger);
        game_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Customer Verified",Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
                verify();

            }
        });

//        //Browser
//        ImageButton browser_btn = (ImageButton)win.findViewById(R.id.browser);
//        browser_btn.setOnClickListener(new OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//
//        //Email
//        ImageButton email_btn = (ImageButton)win.findViewById(R.id.email);
//        email_btn.setOnClickListener(new OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//            }
//        });
    }


    @Override
    public void onBackPressed() {
        if (wv_webview.canGoBack()) {
            wv_webview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            nosignal.setVisibility(View.INVISIBLE);
            wv_webview.setVisibility(View.VISIBLE);
        } else {
            nosignal.setVisibility(View.VISIBLE);
            wv_webview.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }
//todo need to change min sdk to 20 for work with fingerprint
    /*final AuthenticationCallback callback = new AuthenticationCallback() {
        @Override
        public void fingerprintAuthenticationNotSupported() {
            // Device doesn't support fingerprint authentication. May be device doesn't have fingerprint hardware or device is running on Android below Marshmallow.
            // Switch to alternate authentication method.
            Toast.makeText(getApplicationContext(), "Not Supported Device", Toast.LENGTH_LONG).show();
            finish();
            startActivity(getIntent());
            verify();
        }

        @Override
        public void hasNoFingerprintEnrolled() {
            // User has no fingerprint enrolled.
            // Application should redirect the user to the lock screen settings.
            // FingerprintUtils.openSecuritySettings(this)
            finish();
            startActivity(getIntent());
            verify();

        }

        @Override
        public void onAuthenticationError(final int errorCode, @Nullable final CharSequence errString) {
            // Unrecoverable error. Cannot use fingerprint scanner. Library will stop scanning for the fingerprint after this callback.
            // Switch to alternate authentication method.
            finish();
            startActivity(getIntent());
            verify();
        }

        @Override
        public void onAuthenticationHelp(final int helpCode, @Nullable final CharSequence helpString) {
            // Authentication process has some warning. such as "Sensor dirty, please clean it."
            // Handle it if you want. Library will continue scanning for the fingerprint after this callback.
            finish();
            startActivity(getIntent());
            verify();
        }

        @Override
        public void authenticationCanceledByUser() {
            Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_LONG).show();
            // User canceled the authentication by tapping on the cancel button (which is at the bottom of the dialog).
            finish();
            startActivity(getIntent());
        }

        @Override
        public void onAuthenticationSucceeded() {
            // Authentication success
            // Your user is now authenticated.
            Toast.makeText(getApplicationContext(), "Customer Verified", Toast.LENGTH_LONG).show();
            verify();
        }

        @Override
        public void onAuthenticationFailed() {

            Toast.makeText(getApplicationContext(), "Customer Verified", Toast.LENGTH_LONG).show();
            verify();
            finish();
            startActivity(getIntent());


            // Authentication failed.
            // Library will continue scanning the fingerprint after this callback.
        }
    };*/

    public void verify() {
        locationTracker = new LocationTracker("my.action")
//                        .setInterval(50000)
                .setInterval(50000)
                .setGps(true)
                .setNetWork(false)

                // IF YOU WANT JUST CURRENT LOCATION
                .currentLocation(new CurrentLocationReceiver(new CurrentLocationListener() {

                    @Override
                    public void onCurrentLocation(Location location) {
                        Log.d("callback", ":onCurrentLocation" + location.getLongitude());
                        locationTracker.stopLocationService(getBaseContext());
                        dowork(location.getLatitude(), location.getLongitude());
                    }

                    @Override
                    public void onPermissionDiened() {
                        Log.d("callback", ":onPermissionDiened");
                        locationTracker.stopLocationService(getBaseContext());
                    }
                }))


                .start(getBaseContext(), this);
    }

    public void dowork(double lat, double lon) {
        Call<TrackRes> call = apiInterface.performverify(sharedpreferences.getInt(Uid, 0),muserid, lat, lon);
        call.enqueue(new Callback<TrackRes>() {
            @Override
            public void onResponse(Call<TrackRes> call, Response<TrackRes> response) {

                if (response.isSuccessful()) {
                    if (response.body().getResponse().equals("inserted")) {
                        Log.d("verify", "inserted");
                    } else if (response.body().getResponse().equals("not inserted")) {
                        Log.d("verify", "not inserted");
                    } else {
                        Log.d("verify", "not working");
                    }

                } else {
                    Log.d("verify", "no response");
                }
            }

            @Override
            public void onFailure(Call<TrackRes> call, Throwable t) {
                t.printStackTrace();
                Log.d("tracking", "check your internet connection");
            }
        });
    }

}
