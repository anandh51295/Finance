package com.ingenioustechnologies.finance.needs;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.ingenioustechnologies.finance.R;
import com.ingenioustechnologies.finance.api.ApiClient;
import com.ingenioustechnologies.finance.api.ApiInterface;
import com.ingenioustechnologies.finance.model.TrackRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ingenioustechnologies.finance.App.CHANNEL_ID;


public class LocationNeeds extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = "Location Test";
    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";

    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";
    private static final String TAG_FOREGROUND_SERVICE = "FOREGROUND_SERVICE";

    protected GoogleApiClient mGoogleApiClient;

    protected LocationRequest mLocationRequest;

    protected Location mCurrentLocation;

    protected long interval;

//    PowerManager.WakeLock wakeLock;
//    PowerManager powerManager;

    public static ApiInterface apiInterface;
    SharedPreferences sharedpreferences;
    public static final String Name = "nameKey";
    public static final String PWD = "passwordKey";
    public static final String Uid = "useridKey";
    public static final String Userrole = "userroleKey";
    public static final String mypreference = "financesharedpref";

    protected Boolean gps;

    protected Boolean netWork;


    @Override
    public void onCreate() {
        super.onCreate();

//        try {
//            powerManager = (PowerManager) getSystemService(POWER_SERVICE);
//            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
//                    "MyApp::MyWakelockTag");
//            wakeLock.acquire();
//        } catch (Exception i) {
//            i.printStackTrace();
//        }

    }

    /* Used to build and start foreground service. */
    private void startForegroundService() {
        Log.d(TAG_FOREGROUND_SERVICE, "Start foreground service.");

        // Create notification default intent.
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Create notification builder.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle("Finance");
        // Make notification show big text.
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("Finance");
        bigTextStyle.bigText("Location Tracking in Running");
        // Set big text style.
        builder.setStyle(bigTextStyle);

        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);

        // Make the notification max priority.
        builder.setPriority(Notification.PRIORITY_MAX);
        // Make head-up notification.
        builder.setFullScreenIntent(pendingIntent, true);


        // Build the notification.
        Notification notification = builder.build();

        // Start foreground service.
        startForeground(1, notification);
    }

    private void stopForegroundService() {
        Log.d(TAG_FOREGROUND_SERVICE, "Stop foreground service.");

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
        try {
            stopLocationUpdates();
            mGoogleApiClient.disconnect();
        } catch (Exception r) {
            r.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        gps = true;
        netWork = true;
        interval = 180000;

        buildGoogleApiClient();

        mGoogleApiClient.connect();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
        if (intent != null) {
            String action = intent.getAction();

            switch (action) {
                case ACTION_START_FOREGROUND_SERVICE:
                    startForegroundService();
                    Toast.makeText(getApplicationContext(), "Finance service is started.", Toast.LENGTH_LONG).show();
                    break;
                case ACTION_STOP_FOREGROUND_SERVICE:
                    stopForegroundService();
                    Toast.makeText(getApplicationContext(), "Finance service is stopped.", Toast.LENGTH_LONG).show();
                    break;
            }
        }
        return START_STICKY;
    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(interval);
        mLocationRequest.setFastestInterval(interval / 2);
        if (gps) {
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        } else if (netWork) {
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }
    }

    protected void startLocationUpdates() {
        try {
            if (mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        } catch (SecurityException ex) {
        }
    }

    private void updateService() {
        if (null != mCurrentLocation) {
            sendLocationBroadcast(this.mCurrentLocation);
            sendCurrentLocationBroadCast(this.mCurrentLocation);
            Log.d("myInfo: ", "send broadcast location data");
        } else {
            sendPermissionDeinedBroadCast();
            Log.d("Error: ", "Permission deined");
        }
    }

    private void sendLocationBroadcast(Location sbLocationData) {
//        Intent locationIntent = new Intent();
//        locationIntent.setAction(this.actionReceiver);
        //locationIntent.putExtra(SettingsLocationTracker.LOCATION_MESSAGE, sbLocationData);
//        sendBroadcast(locationIntent);
        Log.d("Location: ", "Latitude: " + sbLocationData.getLatitude() + "Longitude:" + sbLocationData.getLongitude());
        if (sharedpreferences.contains(Userrole)) {
            if (!sharedpreferences.getString(Userrole, null).equals("admin")) {
                sendtrack(sharedpreferences.getInt(Uid, 0), sbLocationData.getLatitude(), sbLocationData.getLongitude());
            }
        }
    }

    private void sendCurrentLocationBroadCast(Location sbLocationData) {
        Log.d("Location: ", "Latitude: " + sbLocationData.getLatitude() + "Longitude:" + sbLocationData.getLongitude());
        if (sharedpreferences.contains(Userrole)) {
            if (!sharedpreferences.getString(Userrole, null).equals("admin")) {
                sendtrack(sharedpreferences.getInt(Uid, 0), sbLocationData.getLatitude(), sbLocationData.getLongitude());
            }
        }
    }

    private void sendPermissionDeinedBroadCast() {
//        try {
//            LocationRequest request = new LocationRequest();
//            request.setInterval(interval);
//            request.setFastestInterval(interval/2);
//            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
//            int permission = ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION);
//            if (permission == PackageManager.PERMISSION_GRANTED) {
//                // Request location updates and when an update is
//                client.requestLocationUpdates(request, new LocationCallback() {
//                    @Override
//                    public void onLocationResult(LocationResult locationResult) {
//                        Location location = locationResult.getLastLocation();
//                        if (location != null) {
//                            Log.d("myInfo: ", "send broadcast location data");
//                            sendLocationBroadcast(location);
//                            sendCurrentLocationBroadCast(location);
//                        }
//                    }
//                }, null);
//            } else {
//                Log.d("Error: ", "Permission deined Again");
//            }
//        } catch (Exception p) {
//            p.printStackTrace();
//        }
    }

    protected void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onDestroy() {

        try {
            stopLocationUpdates();
            mGoogleApiClient.disconnect();
        } catch (Exception r) {
            r.printStackTrace();
        }
//        wakeLock.release();
        super.onDestroy();
    }

    @Override
    public void onConnected(Bundle connectionHint) throws SecurityException {
        Log.i(TAG, "Connected to GoogleApiClient");
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//            Log.d("Location: ", "Latitude: " + mCurrentLocation.getLatitude() + "Longitude:" + mCurrentLocation.getLongitude());
            updateService();
        }
        startLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateService();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void sendtrack(int userid, Double latitude, Double longitude) {
        Call<TrackRes> call = apiInterface.performtrack(userid, latitude, longitude);
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