package com.ingenioustechnologies.finance;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ingenioustechnologies.finance.needs.LocationNeeds;

public class MainActivity extends AppCompatActivity {
    private LocationReceiver locationReceiver = null;

//    private LocationTracker locationTracker;
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8, cardView9, cardView10, cardView11;
    TextView textView;
    String username, password;
    SharedPreferences sharedpreferences;
    public static final String Name = "nameKey";
    public static final String PWD = "passwordKey";
    public static final String Uid = "useridKey";
    public static final String Userrole = "userroleKey";
    public static final String mypreference = "financesharedpref";

    public static final int PERMISSIONS_REQUEST_CODE = 0;


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
        cardView8 = findViewById(R.id.ccard);
        textView = findViewById(R.id.welcometxt);
        cardView9 = findViewById(R.id.ndeletecard);
        cardView10 = findViewById(R.id.nloancard);
        cardView11 = findViewById(R.id.weekdue);

        IntentFilter intentFilter = new IntentFilter();


        // Set broadcast receiver priority.
        intentFilter.setPriority(100);

        // Create a network change broadcast receiver.
        locationReceiver = new LocationReceiver();

        // Register the broadcast receiver with the intent filter object.
        registerReceiver(locationReceiver, intentFilter);

        Log.d("receiver", "onCreate: screenOnOffReceiver is registered.");



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
            cardView7.setVisibility(View.GONE);
            cardView8.setVisibility(View.GONE);
            cardView10.setVisibility(View.GONE);
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
        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FinduserActivity.class);
                startActivity(intent);
            }
        });

        cardView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                if (!username.isEmpty()) {
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("url", "delete");
                }
                startActivity(intent);
            }
        });

        cardView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                if (!username.isEmpty()) {
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("url", "loans2");
                }
                startActivity(intent);
            }
        });
        cardView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                if (!username.isEmpty()) {
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    intent.putExtra("url", "dues2");
                }
                startActivity(intent);
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
    }


    private void checkPermissionsAndOpenFilePicker() {
        String permission;
        //todo support for 8.0 and above added with below methods
        if (Build.VERSION.SDK_INT >= 26) {
            permission = Manifest.permission.ACCESS_FINE_LOCATION;
        }else{
            permission = Manifest.permission.ACCESS_COARSE_LOCATION;
        }

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                showError();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            domain();
        }
    }
    public void domain(){
        if (sharedpreferences.contains(Userrole)) {
            if (sharedpreferences.getString(Userrole, null).equals("user")) {
//                locationTracker = new LocationTracker("my.action")
//                        .setInterval(60000)
//                        .setGps(true)
//                        .setNetWork(false)
//                        .start(getBaseContext());
                if (!isServiceRunning(LocationNeeds.class)) {
//                    startService(new Intent(this, LocationNeeds.class));
                    Intent intent = new Intent(MainActivity.this, LocationNeeds.class);
                    intent.setAction(LocationNeeds.ACTION_START_FOREGROUND_SERVICE);
                    startService(intent);
                }

            } else {
                Log.d("role", "admin");
            }
        }
    }
    private void showError() {
        Toast.makeText(this, "Allow Location Permission", Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onStart() {
        super.onStart();
//        requestLocationPermission();

        if (sharedpreferences.contains(Userrole)) {
            if (sharedpreferences.getString(Userrole, null).equals("user")) {
                checkPermissionsAndOpenFilePicker();
            } else {
                Log.d("role", "admin");
            }
        }
    }

    public boolean isServiceRunning(Class serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    domain();
//                    locationTracker.onRequestPermission(requestCode, permissions, grantResults);
                } else {
                    showError();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
//            Intent myService = new Intent(MainActivity.this, LocationNeeds.class);
//            stopService(myService);

            Intent intent = new Intent(MainActivity.this, LocationNeeds.class);
            intent.setAction(LocationNeeds.ACTION_STOP_FOREGROUND_SERVICE);
            startService(intent);

            if(locationReceiver!=null)
            {
                unregisterReceiver(locationReceiver);
                Log.d("receiver", "onDestroy: screenOnOffReceiver is unregistered.");
            }
//            locationTracker.stopLocationService(this);
            Log.d("finance","Destroy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
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
//                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                                startActivity(intent);
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            MainActivity.this.finish();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                finishAffinity();
                            }
                        } catch (Exception er) {
                            er.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}
