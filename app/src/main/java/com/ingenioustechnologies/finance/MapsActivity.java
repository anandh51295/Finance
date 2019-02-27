package com.ingenioustechnologies.finance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ingenioustechnologies.finance.api.ApiClient;
import com.ingenioustechnologies.finance.api.ApiInterface;
import com.ingenioustechnologies.finance.model.LocRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static ApiInterface apiInterface;
    String username;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        try {
            Intent intent = getIntent();
            username = intent.getStringExtra("username");
            userid = intent.getIntExtra("userid", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getlocations(userid);
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void getlocations(int userid) {

        Call<LocRes> call = apiInterface.performlocation(userid);
        call.enqueue(new Callback<LocRes>() {
            @Override
            public void onResponse(Call<LocRes> call, Response<LocRes> response) {
                if (response.isSuccessful()) {
                    if(!response.body().getResponse().isEmpty()){
                        int rsize=response.body().getResponse().size();
                        for(int k=0;k<rsize;k++){
                            LatLng sydney = new LatLng(Double.parseDouble(response.body().getResponse().get(k).getLatitude()),Double.parseDouble(response.body().getResponse().get(k).getLongitude()));
                            mMap.addMarker(new MarkerOptions().position(sydney).title(response.body().getResponse().get(k).getDate()));
                            if(k==rsize-1){
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                            }
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_LONG).show();
                        Log.d("get locations","response empty");
                    }
                } else {
                    Log.d("get locations","no response");
                    Toast.makeText(getApplicationContext(),"Check Your Internet Connection or Try Again",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LocRes> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }
}