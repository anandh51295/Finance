package com.ingenioustechnologies.finance;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class ReportActivity extends AppCompatActivity {

    Spinner spinner;
    TextView sdate,edate,heading;
    Button btn,cbtn;
    String username;
    int userid;
    String ed,sd,sel_txt;
    ArrayList<String> uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        try {
            Intent intent = getIntent();
            username = intent.getStringExtra("username");
            userid = intent.getIntExtra("userid", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle(username);
        spinner=findViewById(R.id.opt_spinner);
        sdate=findViewById(R.id.start_txt);
        edate=findViewById(R.id.end_txt);
        btn=findViewById(R.id.s_btn);
        heading=findViewById(R.id.r_head);
        cbtn=findViewById(R.id.btn_cur);
        uname=new ArrayList<>();
        uname.add("all");
        uname.add("UserTracking");
        uname.add("VerificationTracking");

        final ArrayAdapter<String> langAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, uname);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(langAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sel_txt=uname.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(ReportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;
                        ed = selectedyear + "/" + selectedmonth + "/" + selectedday;
                        edate.setText(ed);

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });
        sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(ReportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;
                        sd = selectedyear + "/" + selectedmonth + "/" + selectedday;
                        sdate.setText(sd);

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ReportActivity.this,MapsActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("type",sel_txt);
                intent.putExtra("sdate",sd);
                intent.putExtra("edate",ed);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        cbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ReportActivity.this,CurrentlocationActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });

    }
}
