package com.ingenioustechnologies.finance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

import com.ingenioustechnologies.finance.api.ApiClient;
import com.ingenioustechnologies.finance.api.ApiInterface;
import com.ingenioustechnologies.finance.model.TrackRes;

import br.com.safety.locationlistenerhelper.core.SettingsLocationTracker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationReceiver extends BroadcastReceiver {
    public static ApiInterface apiInterface;
    SharedPreferences sharedpreferences;
    public static final String Name = "nameKey";
    public static final String PWD = "passwordKey";
    public static final String Uid = "useridKey";
    public static final String Userrole = "userroleKey";
    public static final String mypreference = "financesharedpref";
    //todo this page might not need
    @Override
    public void onReceive(Context context, Intent intent) {
        if (null != intent && intent.getAction().equals("my.action")) {
            Location locationData = (Location) intent.getParcelableExtra(SettingsLocationTracker.LOCATION_MESSAGE);
            Log.d("LocationR: ", "Latitude: " + locationData.getLatitude() + "Longitude:" + locationData.getLongitude());
            //send your call to api or do any things with the of location data
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            sharedpreferences = context.getSharedPreferences(mypreference,
                    Context.MODE_PRIVATE);
            if(sharedpreferences.contains(Userrole)){
                if(!sharedpreferences.getString(Userrole,null).equals("admin")){
                    sendtrack(sharedpreferences.getInt(Uid, 0),locationData.getLatitude(),locationData.getLongitude());
                }
            }

        }
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