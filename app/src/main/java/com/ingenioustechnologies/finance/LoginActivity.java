package com.ingenioustechnologies.finance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ingenioustechnologies.finance.api.ApiClient;
import com.ingenioustechnologies.finance.api.ApiInterface;
import com.ingenioustechnologies.finance.model.LoginRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static ApiInterface apiInterface;
    ProgressBar pb;
    TextView heads;
    EditText username, password;
    Button login_btn;
    String user, pass, key, userrole;
    int userid;
    SharedPreferences sharedpreferences;
    public static final String Name = "nameKey";
    public static final String PWD = "passwordKey";
    public static final String Uid = "useridKey";
    public static final String Userrole = "userroleKey";
    public static final String Vkey = "versionKey";
    public static final String mypreference = "financesharedpref";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        key = "MOBILEAPP";
        pb = findViewById(R.id.progressBar);
        heads = findViewById(R.id.tv_head);
        username = findViewById(R.id.userText);
        password = findViewById(R.id.passwordText);
        login_btn = findViewById(R.id.loginbutton);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        try {
            if (!sharedpreferences.getString(Name, null).isEmpty() && !sharedpreferences.getString(PWD, null).isEmpty()) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                user = username.getText().toString();
                pass = password.getText().toString();

                if (!user.isEmpty() || !pass.isEmpty()) {
                    try {
                        checklogin(user, pass, key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    public void checklogin(final String username, final String password, String key) {
        Call<LoginRes> call = apiInterface.performlogin(username, password, key);
        call.enqueue(new Callback<LoginRes>() {
            @Override
            public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                if (response.isSuccessful()) {
                    pb.setVisibility(View.INVISIBLE);
                    try {
                        if (response.body().isStatus()) {
                            userid = response.body().getUserid();
                            userrole = response.body().getUserrole();
                            save(username, password, userid, userrole);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            //intent.putExtra("userrole",userrole);
                            //intent.putExtra("userid",userid);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Provide username and password", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    pb.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Check Your Internet Connection1", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginRes> call, Throwable t) {
                pb.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Check Your Internet Connection2", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }


    public void save(String username, String password, int id, String role) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (Build.VERSION.SDK_INT >= 23) {
            editor.putString(Vkey,"yes");
        } else {
            editor.putString(Vkey,"no");
        }
        editor.putString(Name, username);
        editor.putString(PWD, password);
        editor.putInt(Uid, id);
        editor.putString(Userrole, role);
        editor.commit();
    }

}