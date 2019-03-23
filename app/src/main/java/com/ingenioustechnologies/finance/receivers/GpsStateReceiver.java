package com.ingenioustechnologies.finance.receivers;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ingenioustechnologies.finance.service.LocationService;

public class GpsStateReceiver extends BroadcastReceiver {

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            //mContext.startService(new Intent(mContext,SystemService.class));
            //if(!isServiceRunning(DataService.class)){
            //    mContext.startService(new Intent(mContext,DataService.class));
            //}
            mContext.startService(new Intent(mContext, LocationService.class));
        }
    }

    public boolean isServiceRunning(Class serviceClass) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}


