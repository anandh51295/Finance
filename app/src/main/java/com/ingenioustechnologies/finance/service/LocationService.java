package com.ingenioustechnologies.finance.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.ingenioustechnologies.finance.api.ApiClient;
import com.ingenioustechnologies.finance.api.ApiInterface;
import com.ingenioustechnologies.finance.model.TrackRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationService extends Service implements LocationListener {

    private Context mContext;

    private CheckPositions positions;

    private Location location;
    private LocationManager locationManager;

    public static ApiInterface apiInterface;
    SharedPreferences sharedpreferences;
    public static final String Name = "nameKey";
    public static final String PWD = "passwordKey";
    public static final String Uid = "useridKey";
    public static final String Userrole = "userroleKey";
    public static final String mypreference = "financesharedpref";

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 300; // 500 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 0; // 0 secs

    // flag for GPS status
    boolean isGPSEnabled = false;
    // flag for network status
    boolean isNetworkEnabled = false;
    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;

    @Override
    public void onCreate(){
        super.onCreate();
        mContext = this;
        positions = new CheckPositions();
//        handler = new Handler();
//        runnable = new Runnable() {
//            public void run() {
//                Log.d("service","still running");
//                getLocation();
//                handler.postDelayed(runnable, 10000);
//            }
//        };
//
//        handler.postDelayed(runnable, 15000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        getLocation();
        positions.setLatitude("0");
        positions.setLongitude("0");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public Location getLocation(){
        try{
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(!isGPSEnabled && !isNetworkEnabled){
            }else{
                if(isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }else if(isNetworkEnabled){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                }
            }
        }catch (Exception e){
        }
        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        try {

            String latitude = String.valueOf(location.getLatitude());
            String longitude = String.valueOf(location.getLongitude());
            if(!latitude.equals(positions.getLatitude()) && !longitude.equals(positions.getLongitude())){
                sendtrack(sharedpreferences.getInt(Uid, 0),latitude,longitude);
                Log.d("watch","working");
            }
        }catch (Exception e){
                Log.d("watch","not working");
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public class CheckPositions{

        String latitude,longitude;

        public CheckPositions(){
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }
    }
    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
//        handler.removeCallbacks(runnable);
        Log.d("service","stopped");
        //Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }
    public void sendtrack(int userid, String latitude, String longitude) {
        Call<TrackRes> call = apiInterface.performstrack(userid, latitude, longitude);
        call.enqueue(new Callback<TrackRes>() {
            @Override
            public void onResponse(Call<TrackRes> call, Response<TrackRes> response) {

                if (response.isSuccessful()) {
                    try {

                        if (response.body().getResponse().equals("inserted")) {
                            Log.d("tracking", " inserted");
                        } else if (response.body().getResponse().equals("not inserted")) {
                            Log.d("tracking", "not inserted");
                        } else {
                            Log.d("tracking", "not working");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("tracking", "no response");
                    Log.d("check", String.valueOf(response.raw().body()));
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
