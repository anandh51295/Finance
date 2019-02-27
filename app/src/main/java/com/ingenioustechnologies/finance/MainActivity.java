package com.ingenioustechnologies.finance;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import br.com.safety.locationlistenerhelper.core.LocationTracker;

public class MainActivity extends AppCompatActivity {

    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7;
    TextView textView;
    String username, password;
    SharedPreferences sharedpreferences;
    public static final String Name = "nameKey";
    public static final String PWD = "passwordKey";
    public static final String Uid = "useridKey";
    public static final String Userrole = "userroleKey";
    public static final String mypreference = "financesharedpref";
    LocationTracker locationTracker;
    private final int REQUEST_LOCATION_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Dashboard");
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        cardView1 = findViewById(R.id.partiescard);
        cardView2 = findViewById(R.id.loanscard);
        cardView3 = findViewById(R.id.vloanscard);
        cardView4 = findViewById(R.id.viewduescard);
        cardView5 = findViewById(R.id.vduescard);
        cardView6 = findViewById(R.id.usercard);
        cardView7 = findViewById(R.id.ucard);

        textView = findViewById(R.id.welcometxt);


        if (sharedpreferences.contains(Name) && sharedpreferences.contains(PWD) && sharedpreferences.contains(Uid) && sharedpreferences.contains(Userrole)) {
            String vals = "Welcome " + sharedpreferences.getString(Name, null);
            textView.setText(vals);
            username = sharedpreferences.getString(Name, null);
            password = sharedpreferences.getString(PWD, null);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        if (sharedpreferences.getString(Userrole, null).equals("user")) {
            cardView6.setVisibility(View.GONE);
        }
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                if (!username.isEmpty()) {
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("url", "parties");
                }
                startActivity(intent);

            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                if (!username.isEmpty()) {
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("url", "loans");
                }
                startActivity(intent);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                if (!username.isEmpty()) {
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("url", "loans1");
                }
                startActivity(intent);
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                if (!username.isEmpty()) {
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("url", "dues");
                }
                startActivity(intent);
            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                if (!username.isEmpty()) {
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("url", "dues1");
                }
                startActivity(intent);
            }
        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                if (!username.isEmpty()) {
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("url", "users");
                }
                startActivity(intent);
            }
        });
        cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserlocActivity.class);
                startActivity(intent);
            }
        });


    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        // Forward results to EasyPermissions
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }
//
//    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
//    public void requestLocationPermission() {
//        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
//        if(EasyPermissions.hasPermissions(this, perms)) {
//            //Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
//        }
//    }


    @Override
    protected void onStart() {
        super.onStart();
//        requestLocationPermission();
        if (sharedpreferences.contains(Userrole)) {
            if (sharedpreferences.getString(Userrole, null).equals("user")) {
//                Intent i2 = new Intent(getApplicationContext(), LocationService.class);
//                startService(i2);
                locationTracker = new LocationTracker("my.action")
//                        .setInterval(50000)
                        .setInterval(60000)
                        .setGps(true)
                        .setNetWork(false)

                        // IF YOU WANT JUST CURRENT LOCATION
                        // .currentLocation(new CurrentLocationReceiver(new CurrentLocationListener() {
                        //
                        //            @Override
                        //            public void onCurrentLocation(Location location) {
                        //               Log.d("callback", ":onCurrentLocation" + location.getLongitude());
                        //               locationTracker.stopLocationService(getBaseContext());
                        //            }
                        //
                        //            @Override
                        //            public void onPermissionDiened() {
                        //                Log.d("callback", ":onPermissionDiened");
                        //                locationTracker.stopLocationService(getBaseContext());
                        //            }
                        // }))


                        .start(getBaseContext(), this);

                // IF YOU WANT RUN IN SERVICE
//                        .start(this);
            } else {
                Log.d("role", "admin");
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationTracker.onRequestPermission(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            locationTracker.stopLocationService(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
//        MenuItem shareItem = menu.findItem(R.id.check_out);
//                shareItem.setVisible(false);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.check_out) {

            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to Logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                sharedpreferences = getApplicationContext().getSharedPreferences(mypreference,
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (Exception er) {
                                er.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}
