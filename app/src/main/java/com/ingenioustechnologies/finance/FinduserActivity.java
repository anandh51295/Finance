package com.ingenioustechnologies.finance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ingenioustechnologies.finance.api.ApiClient;
import com.ingenioustechnologies.finance.api.ApiInterface;
import com.ingenioustechnologies.finance.model.CustomerRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinduserActivity extends AppCompatActivity {

    AutoCompleteTextView tv1,tv2;
    public static ApiInterface apiInterface;
    TextView thead;
    Button btn;
    ArrayList<String> username;
    ArrayList<String> usernumber;
    String sel_name,sel_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finduser);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        tv1=findViewById(R.id.cac_txt);
        tv2=findViewById(R.id.cac_number_txt);
        thead=findViewById(R.id.cac_txt);
        btn=findViewById(R.id.cac_btn);
        username= new ArrayList<>();
        usernumber=new ArrayList<>();
        username.add("username");
        usernumber.add("mobilenumber");
        getdata();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,username);
        tv1.setThreshold(1);
        tv1.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item,usernumber);
        tv2.setThreshold(1);
        tv2.setAdapter(adapter1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname,unum;
                uname=tv1.getText().toString();
                unum=tv2.getText().toString();
                if(!uname.isEmpty()){
                    sel_name=uname;
                    Intent intent=new Intent(FinduserActivity.this,NmapsActivity.class);
                    intent.putExtra("val",sel_name);
                    intent.putExtra("type","name");
                    startActivity(intent);
                }else if(!unum.isEmpty()){
                    sel_num=unum;
                    Intent intent=new Intent(FinduserActivity.this,NmapsActivity.class);
                    intent.putExtra("val",sel_num);
                    intent.putExtra("type","number");
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Please enter username or mobile number",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void getdata(){
        Call<CustomerRes> call = apiInterface.performallverifyuser();
        call.enqueue(new Callback<CustomerRes>() {
            @Override
            public void onResponse(Call<CustomerRes> call, Response<CustomerRes> response) {

                if (response.isSuccessful()) {
                    if(!response.body().getResponse().isEmpty()){

                        int vsize=response.body().getResponse().size();
                        for(int k=0;k<vsize;k++){
                            username.add(response.body().getResponse().get(k).getCustomername());
                            usernumber.add(response.body().getResponse().get(k).getNumbers());
                        }
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<CustomerRes> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_LONG).show();
            }
        });
    }
}
