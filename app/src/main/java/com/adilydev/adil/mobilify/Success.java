package com.adilydev.adil.mobilify;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class Success extends AppCompatActivity {

    TextView tv, tv2, tvUid, tvDate;
    UUID uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        tv = (TextView) findViewById(R.id.success2);
        tv2 = (TextView) findViewById(R.id.success3);
        tvUid = (TextView) findViewById(R.id.tvUid);
        tvDate = (TextView) findViewById(R.id.tvDate);

        // Date date = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());

        tv.setText(getIntent().getStringExtra("MyText"));
        tv2.setText(getIntent().getStringExtra("getData"));
        tvUid.setText(getIntent().getStringExtra("uniqueID"));
        tvDate.setText(date);
    }

}
