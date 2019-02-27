package com.ingenioustechnologies.finance.service;

import android.app.IntentService;
import android.content.Intent;

import br.com.safety.locationlistenerhelper.core.LocationTracker;


public class LocationService extends IntentService {
    LocationTracker locationTracker;
    public LocationService() {
        super("LocationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        {
            //todo this page not needed
            locationTracker=new LocationTracker("my.action")
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


//                    .start(getBaseContext(), this);

            // IF YOU WANT RUN IN SERVICE
         .start(this);
        }
    }


}
