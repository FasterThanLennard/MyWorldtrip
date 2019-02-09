package com.rhahn.myworldtrip.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.rhahn.myworldtrip.DataHandler.Datapersistance;
import com.rhahn.myworldtrip.DataHandler.Datarequest;
import com.rhahn.myworldtrip.R;

public class LicenseActivity extends AppCompatActivity {
    TextView tvLicense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.actionbar_background));
        getSupportActionBar().setTitle(R.string.license);
        tvLicense = findViewById(R.id.tvLicense);

        tvLicense.setText(Datarequest.getFileAsString(this, R.raw.license));

    }
}
