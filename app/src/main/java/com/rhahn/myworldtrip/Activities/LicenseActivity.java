package com.rhahn.myworldtrip.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rhahn.myworldtrip.R;

public class LicenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        getSupportActionBar().setTitle(R.string.license);
    }
}
