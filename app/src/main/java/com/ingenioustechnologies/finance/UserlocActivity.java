package com.ingenioustechnologies.finance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ingenioustechnologies.finance.adopter.UserAdopter;
import com.ingenioustechnologies.finance.api.ApiClient;
import com.ingenioustechnologies.finance.api.ApiInterface;
import com.ingenioustechnologies.finance.model.UserRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserlocActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    public static ApiInterface apiInterface;
    UserAdopter adopter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userloc);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        recyclerView=findViewById(R.id.usrlist);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        getusers();

    }

    public void getusers() {
        Call<UserRes> call = apiInterface.performalluser();
        call.enqueue(new Callback<UserRes>() {
            @Override
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                if (response.isSuccessful()) {
                    if(!response.body().getResponse().isEmpty()){
                        adopter = new UserAdopter(response.body().getResponse());
                        recyclerView.setAdapter(adopter);
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<UserRes> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }
}
