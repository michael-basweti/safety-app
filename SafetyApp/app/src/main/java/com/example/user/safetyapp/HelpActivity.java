package com.example.user.safetyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        mText=(TextView) findViewById(R.id.textView);
        mText.setText("1.To post in this application please make sure you are a registered user."+"\n\n2.Choose your Registration name wisely because it will be displayed in all " +
                "your posts.\n\n3.Make sure you are connected to the internet every time you want to send a help request.\n\n5.Make sure your GPRS is always on.\n\n6.Your Password should exceed six characters.\n\n7.when uploading a display photo,all fields should be filled for it to be uploaded.");

    }
}
